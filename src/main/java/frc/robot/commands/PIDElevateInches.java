package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class PIDElevateInches extends Command {
    
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
    private double driveTicks;
    
    private double driveInches;
//    private double driveP;
//    private double driveI;
//    private double driveD;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    
    private final static double ENCODER_TICKS_PER_REVOLUTION = 4096;

    private static final double MAX_PERCENT_VOLTAGE = 1.0; //was 12 (volts previously, now the input is percent)
    private static final double MIN_PERCENT_VOLTAGE = 0.0; //was 1.9 (volts perviously, now the input is percent)

    //STOP_THRESHOLD_REAL was 3 inches and is now 8 inches in an attempt to cut back on time
    private final static double STOP_THRESHOLD_REAL = 3; //3.0;
    private final static double STOP_THRESHOLD_ADJUSTED = Robot.ELEVATOR_SUBSYSTEM.convertInchesToRevs(STOP_THRESHOLD_REAL * ENCODER_TICKS_PER_REVOLUTION);
    
//    private final static int I_ZONE_IN_REVOLUTIONS = 50; //100;
    
    private final static int allowableCloseLoopError = 1;
    
    private int onTargetCount = 0;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

    private double specificDistanceP = 0.5;
    
    private double specificDistanceI = 0;
    
    private double specificDistanceD = .1;
    
    private double specificDistanceF = .199;
    
    public PIDElevateInches(double inches, boolean reverse) {
        requires(Robot.ELEVATOR_SUBSYSTEM);
          
//      this.driveTicks = inches / ENCODER_TICKS_PER_REVOLUTION;
    
        if (reverse) {
            this.driveTicks = -Robot.ELEVATOR_SUBSYSTEM.applyGearRatio(Robot.ELEVATOR_SUBSYSTEM.convertInchesToRevs(inches * ENCODER_TICKS_PER_REVOLUTION));//input now has to be ticks instead of revolutions which is why we multiply by 4096
        } else {
            this.driveTicks = Robot.ELEVATOR_SUBSYSTEM.applyGearRatio(Robot.ELEVATOR_SUBSYSTEM.convertInchesToRevs(inches * ENCODER_TICKS_PER_REVOLUTION));
        }
        
        this.driveInches = inches;
//        this.driveP = specificDistanceP;
//        this.driveI = specificDistanceI;
//        this.driveD = specificDistanceD;
    }
    
        
    protected void initialize() {
        
        onTargetCount = 0;
        
        Robot.ELEVATOR_SUBSYSTEM.resetBothEncoders();
//      Robot.resetNavXAngle();
        Robot.ELEVATOR_SUBSYSTEM.enablePositionControl();
        
        Robot.ELEVATOR_SUBSYSTEM.elevator2.config_kP(pidIdx, specificDistanceP, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.ELEVATOR_SUBSYSTEM.elevator2.config_kI(pidIdx, specificDistanceI, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.ELEVATOR_SUBSYSTEM.elevator2.config_kD(pidIdx, specificDistanceD, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.ELEVATOR_SUBSYSTEM.elevator2.config_kF(pidIdx, specificDistanceF, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.config_kF(pidIdx, specificDistanceF, timeoutMs);
        
//        Robot.ELEVATOR_SUBSYSTEM.talonDriveLeftPrimary.ClearIaccum();
//        Robot.ELEVATOR_SUBSYSTEM.talonDriveRightPrimary.ClearIaccum();
        
        Robot.ELEVATOR_SUBSYSTEM.elevator2.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator2.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator2.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator2.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
//        Robot.ELEVATOR_SUBSYSTEM.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.ELEVATOR_SUBSYSTEM.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.ELEVATOR_SUBSYSTEM.elevator2.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        Robot.ELEVATOR_SUBSYSTEM.elevator1.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
//        Robot.ELEVATOR_SUBSYSTEM.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
//        Robot.ELEVATOR_SUBSYSTEM.talonDriveRightPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
        
        if (driveInches > 30) {
            Robot.ELEVATOR_SUBSYSTEM.elevator2.configMotionCruiseVelocity(10000, timeoutMs); //7500, 20500, 7500, 20000
            Robot.ELEVATOR_SUBSYSTEM.elevator2.configMotionAcceleration(20000, timeoutMs); //test 5000
            Robot.ELEVATOR_SUBSYSTEM.elevator1.configMotionCruiseVelocity(10000, timeoutMs);
            Robot.ELEVATOR_SUBSYSTEM.elevator1.configMotionAcceleration(20000, timeoutMs);
        } else if (driveInches <= 30) {
            Robot.ELEVATOR_SUBSYSTEM.elevator2.configMotionCruiseVelocity(7500, timeoutMs); //7500, 15500, 7500, 15000
            Robot.ELEVATOR_SUBSYSTEM.elevator1.configMotionCruiseVelocity(7500, timeoutMs);
            Robot.ELEVATOR_SUBSYSTEM.elevator1.configMotionAcceleration(15000, timeoutMs);
        }

        Robot.ELEVATOR_SUBSYSTEM.setPID(driveTicks, driveTicks);
    }
    
    protected void execute() {
    }
    
    @Override
    protected boolean isFinished() {
        double leftPosition = Robot.ELEVATOR_SUBSYSTEM.getLeftPosition();
        double rightPosition = Robot.ELEVATOR_SUBSYSTEM.getRightPosition();
        
        if (leftPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && leftPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED) &&
            rightPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && rightPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED)) {
            onTargetCount++;
        } else {
            onTargetCount = 0;
        }
        
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    }
    
    protected void end() {
//        SmartDashboard.putNumber("LEFT FINAL Drive Distance: Inches", Robot.ELEVATOR_SUBSYSTEM.applyGearRatio(Robot.ELEVATOR_SUBSYSTEM.convertRevsToInches(Robot.ELEVATOR_SUBSYSTEM.getLeftPosition())));
//        SmartDashboard.putNumber("RIGHT FINAL Drive Distance: Inches", Robot.ELEVATOR_SUBSYSTEM.applyGearRatio(Robot.ELEVATOR_SUBSYSTEM.convertRevsToInches(Robot.ELEVATOR_SUBSYSTEM.getRightPosition())));
        OI.distance = Math.abs(Robot.ELEVATOR_SUBSYSTEM.averageInchesDriven());
//        SmartDashboard.putNumber("Straight", OI.latestDistanceDriven);
        Robot.ELEVATOR_SUBSYSTEM.enableVBusControl();
        Robot.ELEVATOR_SUBSYSTEM.resetBothEncoders();
//      Robot.resetNavXAngle();
        Robot.ELEVATOR_SUBSYSTEM.stop();
    }
    
    protected void interrupted() {
        end();
    }

}
