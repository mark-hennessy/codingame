package com.codingame;

import java.util.*;

import static java.lang.Math.*;

class MarsLanderLevelTwo {

    public static void main(String args[]) {
//        play();
        debug();
    }

    private static void play() {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        game.start();
    }

    private static void debug() {
        List<String> list = new ArrayList<>();
        list.add("string");
        String[] strings = {"1", "2"};
        for (String s : strings) {
            
        }
    }

    public static class Game {
        private Scanner input;
        private Planet planet;
        private Ship ship;

        public Game(Scanner input) {
            this.input = input;
        }

        public void start() {
            loadGameData();
            // game loop
            while (true) {
                ship.update(input);
            }
        }

        private void loadGameData() {
            planet = new Planet(-3.711, input);
            ship = new Ship(planet);
        }
    }

    public static class Planet {
        private final Vector gravity;
        private List<Vector> surfacePoints;
        private List<Surface> surfaces;

        public Planet(double gravity, Scanner input) {
            this.gravity = new Vector(0, gravity);
            surfacePoints = new ArrayList<>();
            surfaces = new ArrayList<>();
            if (input != null) {
                parseSurfaces(input);
            }
        }

        private void parseSurfaces(Scanner input) {
            int numberOfPoints = input.nextInt();
            Vector previousPoint = null;
            for (int i = 0; i < numberOfPoints; i++) {
                Vector point = new Vector(input.nextInt(), input.nextInt());
                surfacePoints.add(point);
                if (previousPoint != null) {
                    surfaces.add(new Surface(previousPoint, point));
                }
                previousPoint = point;
            }
        }

        public Vector getGravity() {
            return gravity;
        }

        public Surface getFlatSurface() {
            return surfaces.stream().filter(s -> s.isFlat()).findFirst().orElse(null);
        }

        public Surface getCurrentSurface(Vector currentPosition) {
            return surfaces.stream()
                    .filter(s -> s.isCurrentSurface(currentPosition))
                    .findFirst()
                    .orElse(null);
        }

        public Vector getHighestRemainingSurfacePoint(Vector currentPosition) {
            Surface currentSurface = getCurrentSurface(currentPosition);
            Surface flatSurface = getFlatSurface();
            HorizontalDirection horizontalDirection = flatSurface.getHorizontalDirectionRelativeTo(currentSurface);
            List<Vector> remainingSurfacePoints;
            switch (horizontalDirection) {
                case RIGHT:
                    int fromIndex = surfacePoints.indexOf(currentSurface.getPointB());
                    int toIndex = surfacePoints.indexOf(flatSurface.getPointB());
                    remainingSurfacePoints = surfacePoints.subList(fromIndex, toIndex);
                    break;
                case LEFT:
                    fromIndex = surfacePoints.indexOf(flatSurface.getPointB());
                    toIndex = surfacePoints.indexOf(currentSurface.getPointB());
                    remainingSurfacePoints = surfacePoints.subList(fromIndex, toIndex);
                    break;
                default:
                    remainingSurfacePoints = surfacePoints;
                    break;
            }
            return getHighestSurfacePoint(remainingSurfacePoints);
        }

        private Vector getHighestSurfacePoint(List<Vector> surfacePoints) {
            return surfacePoints.stream()
                    .sorted(Comparator.comparing(Vector::getY).reversed())
                    .findFirst()
                    .orElse(new Vector(0, 0));
        }
    }

    public static class Surface {
        private Vector pointA;
        private Vector pointB;

        public Surface(Vector pointA, Vector pointB) {
            this.pointA = pointA;
            this.pointB = pointB;
        }

        public Vector getPointA() {
            return pointA;
        }

        public Vector getPointB() {
            return pointB;
        }

        public Vector getCenter() {
            return new Vector((pointA.getX() + pointB.getX()) / 2, (pointA.getY() + pointB.getY()) / 2);
        }

        public Vector getHighestPoint() {
            return (pointA.getY() > pointB.getY()) ? pointA : pointB;
        }

        public boolean isFlat() {
            return pointA.getY() == pointB.getY();
        }

        public boolean isCurrentSurface(Vector point) {
            return point.getX() >= pointA.getX()
                    && point.getX() <= pointB.getX();
        }

        public double length() {
            return pointA.displacementFrom(pointB);
        }

        public HorizontalDirection getHorizontalDirectionRelativeTo(Surface other) {
            return HorizontalDirection.getDirection(pointA.getX() - other.pointA.getX());
        }

        @Override
        public String toString() {
            return "Surface{" +
                    "pointA=" + pointA +
                    ", pointB=" + pointB +
                    '}';
        }
    }

    public static class Ship {
        private static final double MAX_SAFE_LANDING_VELOCITY_X = 20;
        private static final double MAX_SAFE_CRUISE_VELOCITY_X = 80;
        private static final double MAX_SAFE_LANDING_VELOCITY_Y = 40;
        private static final int MAX_THRUST = 4;
        private static final int MAX_ANGLE_DELTA = 15;

        private Planet planet;
        private Surface flatSurface;
        private Vector startPosition;
        
        private Vector currentPosition;
        private Vector currentVelocity;
        private int currentFuel;
        private int currentAngle;
        private int currentThrust;
        
        private HorizontalDirection currentVelocityHorizontalDirection;
        
        public Ship(Planet planet) {
            this.planet = planet;
            flatSurface = planet.getFlatSurface();
            System.err.println("flatSurface: " + flatSurface);
        }

        public void update(Scanner input) {
            // Update flight data
            currentPosition = new Vector(input.nextInt(), input.nextInt());
            if (startPosition == null) {
                startPosition = currentPosition;
            }
            currentVelocity = new Vector(input.nextInt(), input.nextInt());
            currentFuel = input.nextInt();
            currentAngle = input.nextInt();
            currentThrust = input.nextInt();
            
            currentVelocityHorizontalDirection = getHorizontalDirection(currentVelocity);

            Vector targetPosition = getTargetPosition();
            HorizontalDirection horizontalDirectionToTarget = getHorizontalDirectionTo(targetPosition);
            double newThrust = Number.constrain(currentThrust + 1, 0, MAX_THRUST);

            Vector thrustVector = new Vector(0, 0);
            double thrustAngle = 0;

            boolean aboveFlatSurface = flatSurface.isCurrentSurface(currentPosition);
            boolean nearZeroHorizontalVelocity = abs(currentVelocity.getX()) < 4;
            boolean nearCruisingHorizontalVelocity = abs(currentVelocity.getY()) > MAX_SAFE_CRUISE_VELOCITY_X;
            boolean lowAltitude = abs(currentPosition.verticalDisplacementFrom(targetPosition)) <= MAX_SAFE_LANDING_VELOCITY_Y;
            boolean emergencyLanding = lowAltitude && isXVelocitySafe(currentVelocity);
            double distanceToGround = abs(currentPosition.verticalDisplacementFrom(targetPosition));
            if (aboveFlatSurface) {
                if (nearZeroHorizontalVelocity || emergencyLanding) {
                    Vector currentVelocityPlusGravity = currentVelocity.add(planet.getGravity());
                    if (!isYVelocitySafe(currentVelocityPlusGravity)) {
                        thrustVector = new Vector(0, 4);
                    }
                }
            }
            else {
                double remainingHorizontalDistance = getRemainingHorizontalDistance(targetPosition);
                double horizontalBreakingDistance = calculateHorizontalBreakingdistance(newThrust);
                HorizontalDirection strafeDirection = horizontalDirectionToTarget;
                System.err.println("remainingHorizontalDistance: " + remainingHorizontalDistance);
                System.err.println("horizontalBreakingDistance: " + horizontalBreakingDistance);
                if (isHeadingTowards(targetPosition) && remainingHorizontalDistance <= horizontalBreakingDistance) {
                    strafeDirection = horizontalDirectionToTarget.opposite();
                }
                thrustVector = calculateStrafeThrustVector(newThrust, strafeDirection);
                thrustAngle = calculateThrustAngleInDegrees(thrustVector);
            }
            
            output(thrustVector, thrustAngle);
        }

        private double calculateHorizontalBreakingdistance(double thrust) {
            HorizontalDirection breakDirection = currentVelocityHorizontalDirection.opposite();
            Vector desiredThrust = calculateStrafeThrustVector(thrust, breakDirection);
            double desiredAngle = calculateThrustAngleInDegrees(desiredThrust);
            double angle = currentAngle;
            double thrustX = calculateThrust(thrust, currentAngle).getX();
            double velocityX = currentVelocity.getX();

            // Calculate the displacement needed to come to a stop when the horizontal strafe thrust direction is flipped.
            // We need to account for the update cycles it takes to change the currentAngle to the desiredAngle since the 
            // currentAngle can only change by +-15 per update.
            double totalHorizontalDisplacement = 0;
            while (angle != desiredAngle) {
                if (angle < desiredAngle) {
                    angle = min(angle + MAX_ANGLE_DELTA, desiredAngle);
                } 
                else if (angle > desiredAngle) {
                    angle = max(angle - MAX_ANGLE_DELTA, desiredAngle);
                }
                double initialVelocity = velocityX;
                double finalVelocity = initialVelocity + calculateThrust(thrust, angle).getX();
                // d = averageVelocity * ~1 second
                velocityX = (initialVelocity + finalVelocity) / 2;
                totalHorizontalDisplacement += velocityX;
            }

            double displacementAtDesiredAngle = PhysicsUtil.displacementGivenFinalVelocityAndAcceleration(velocityX, 0, thrustX);
            totalHorizontalDisplacement += displacementAtDesiredAngle;
            
            // Take the absolute value to convert displacement to distance.
            return abs(totalHorizontalDisplacement);
        }

        private Vector calculateStrafeThrustVector(double thrust, HorizontalDirection horizontalDirection) {
            // Counter gravity so we don't gain additional vertical velocity. Note that existing vertical velocity 
            // will not be negated, which is fine since doing so would be inefficient and unnecessary for our use cases.
            double thrustY = -1 * planet.getGravity().getY();
            return calculateThrust(thrust, thrustY, horizontalDirection);
        }

        private Vector calculateThrust(double thrust, double thrustY, HorizontalDirection horizontalDirection) {
            // The sides of a triangle can't be bigger than the hypotenuse!
            thrustY = Number.constrain(thrustY, 0, thrust);
            // h^2 = x^2 + y^2
            // x = sqrt(h^2 - y^2)
            double thrustX = sqrt(pow(thrust, 2) - pow(thrustY, 2));
            if (horizontalDirection == HorizontalDirection.LEFT) {
                thrustX *= -1;
            }
            return new Vector(thrustX, thrustY);
        }

        private Vector calculateThrust(double thrust, double angleInDegrees) {
            // Flip the angle since the game defines that a positive angle goes to the left.
            angleInDegrees *= -1;
            // sin(angle) = thrustX / thrust
            // thrustX = sin(angle) * thrust
            double thrustX = sin(toRadians(angleInDegrees)) * thrust;
            // cos(angle) = thrustY / thrust
            // thrustY = cos(angle) * thrust
            double thrustY = cos(toRadians(angleInDegrees)) * thrust;
            return new Vector(thrustX, thrustY);
        }
        
        private double calculateThrustAngleInDegrees(Vector thrust) {
            // Flip the angle since the game defines that a positive angle goes to the left.
            return -1 * thrust.getAngleFromYAxisInDegrees();
        }

        private boolean isXVelocitySafe(Vector velocity) {
            return abs(Number.roundToInt(velocity.getX())) <= MAX_SAFE_LANDING_VELOCITY_X;
        }
        
        private boolean isYVelocitySafe(Vector velocity) {
            return abs(Number.roundToInt(velocity.getY())) <= MAX_SAFE_LANDING_VELOCITY_Y;
        }

        private boolean isHeadingTowards(Vector targetPosition) {
            return currentVelocityHorizontalDirection == getHorizontalDirectionTo(targetPosition);
        }
        
        private HorizontalDirection getHorizontalDirectionTo(Vector targetPosition) {
            return HorizontalDirection.getDirection(currentPosition.horizontalDisplacementFrom(targetPosition));
        }

        private HorizontalDirection getHorizontalDirection(Vector vector) {
            return HorizontalDirection.getDirection(vector.getX());
        }

        private Vector getTargetPosition() {
            double distanceToPointA = currentPosition.horizontalDisplacementFrom(flatSurface.getPointA());
            double distanceToPointB = currentPosition.horizontalDisplacementFrom(flatSurface.getPointB());
            boolean closerToPointA = distanceToPointA < distanceToPointB;
            return closerToPointA ? flatSurface.getPointA() : flatSurface.getPointB();
        }

        private double getRemainingHorizontalDistance(Vector targetPosition) {
            // Take the absolute value since distance, unlike displacement, does not have a direction.
            return abs(targetPosition.getX() - currentPosition.getX());
        }

        private void output(Vector thrustVector, double thrustAngle) {
            int thrustAngleAsInt = Number.roundToInt(thrustAngle);
            int thrustAsInt = Number.roundToInt(Number.constrain(thrustVector.getMagnitude(), 0, MAX_THRUST));
            System.out.println(thrustAngleAsInt + " " + thrustAsInt);
        }
    }

    public static class Vector {
        private double x;
        private double y;

        public Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public Vector add(Vector other) {
            return new Vector(x + other.x, y + other.y);
        }

        public Vector subtract(Vector other) {
            return new Vector(x - other.x, y - other.y);
        }

        public Vector multiply(double scalar) {
            return new Vector(x * scalar, y * scalar);
        }

        public double horizontalDisplacementFrom(Vector other) {
            return other.getX() - getX();
        }
        
        public double verticalDisplacementFrom(Vector other) {
            return other.getY() - getY();
        }
        
        public double displacementFrom(Vector other) {
            Vector displacementVector = new Vector(horizontalDisplacementFrom(other), verticalDisplacementFrom(other));
            return displacementVector.getMagnitude();
        }

        public double getMagnitude() {
            return sqrt(pow(x, 2) + pow(y, 2));
        }

        public double getAngleFromXAxisInRadians() {
            // angle = asin(opposite / hypotenuse)
            double angle = asin(y / getMagnitude());
            if (x < 0) {
                angle *= -1;
            }
            return angle;
        }

        public double getAngleFromYAxisInRadians() {
            // angle = acos(adjacent / hypotenuse)
            double angle = acos(y / getMagnitude());
            if (x < 0) {
                angle *= -1;
            }
            return angle;
        }

        public double getAngleFromXAxisInDegrees() {
            return toDegrees(getAngleFromXAxisInRadians());
        }

        public double getAngleFromYAxisInDegrees() {
            return toDegrees(getAngleFromYAxisInRadians());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Vector vector = (Vector) o;
            return Double.compare(vector.x, x) == 0 &&
                    Double.compare(vector.y, y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Vector{" +
                    "x=" + x +
                    ", y=" + y +
                    ", magnitude=" + getMagnitude() +
                    ", angleFromYAxisInDegrees=" + getAngleFromYAxisInDegrees() +
                    '}';
        }
    }

    public enum HorizontalDirection {
        LEFT(-1),
        RIGHT(1);

        private int direction;

        HorizontalDirection(int direction) {
            this.direction = direction;
        }

        public static HorizontalDirection getDirection(double number) {
            return number >= 0 ? RIGHT : LEFT;
        }

        public HorizontalDirection opposite() {
            return getDirection(-1 * direction);
        }
    }

    public static class PhysicsUtil {

        public static double timeGivenFinalVelocityAndDisplacement(double vi, double vf, double d) {
            // t = d / v
            double averageVelocity = (vi + vf) / 2;
            return d / averageVelocity;
        }

        public static double timeGivenAccelerationAndDisplacement(double vi, double a, double d) {
            // d = vi * t + (1/2) * a * t^2
            // Quadratic Form
            // 0 = .5 * a * t^2 + vi * t + -d
            return quadraticFormula(.5 * a, vi, -d);
        }

        public static Vector finalVelocityGivenAccelerationAndTime(Vector vi, Vector a, double t) {
            return new Vector(finalVelocityGivenAccelerationAndTime(vi.getX(), a.getX(), t),
                    finalVelocityGivenAccelerationAndTime(vi.getY(), a.getY(), t));
        }

        public static double finalVelocityGivenAccelerationAndTime(double vi, double a, double t) {
            // vf = vi + a * t
            return vi + (a * t);
        }

        public static Vector displacementGivenAccelerationAndTime(Vector vi, Vector a, double t) {
            return new Vector(displacementGivenAccelerationAndTime(vi.getX(), a.getX(), t),
                    displacementGivenAccelerationAndTime(vi.getY(), a.getY(), t));
        }

        public static double displacementGivenAccelerationAndTime(double vi, double a, double t) {
            // d = vi * t + .5 * a * t^2
            return vi * t + .5 * a * pow(t, 2);
        }

        public static Vector displacementGivenFinalVelocityAndAcceleration(Vector vi, Vector vf, Vector a) {
            return new Vector(displacementGivenFinalVelocityAndAcceleration(vi.getX(), vf.getX(), a.getX()),
                    displacementGivenFinalVelocityAndAcceleration(vi.getY(), vf.getY(), a.getY()));
        }

        public static double displacementGivenFinalVelocityAndAcceleration(double vi, double vf, double a) {
            // vf^2 = vi^2 + 2 * a * d
            // d = (vf^2 - vi^2) / (2 * a)
            return (pow(vf, 2) - pow(vi, 2)) / (2 * a);
        }

        public static Vector accelerationGivenFinalVelocityAndTime(Vector vi, Vector vf, double t) {
            return new Vector(accelerationGivenFinalVelocityAndTime(vi.getX(), vf.getX(), t),
                    accelerationGivenFinalVelocityAndTime(vi.getY(), vf.getY(), t));
        }

        public static double accelerationGivenFinalVelocityAndTime(double vi, double vf, double t) {
            // a = (vf - vi) / t
            return (vf - vi) / t;
        }

        public static double quadraticFormula(double a, double b, double c) {
            double t1 = (-b + sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);
            double t2 = (-b - sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);

            if (t1 < 0) {
                return t2;
            }

            if (t2 < 0) {
                return t1;
            }

            return min(t1, t2);
        }
    }

    public static class Number {

        public static int roundToInt(double value) {
            return (int) round(value);
        }

        public static double constrain(double value, double min, double max) {
            value = max(min, value);
            value = min(max, value);
            return value;
        }
    }

}