package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class PIDDriveInchesHoldHatch extends Command {
    
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
    private double driveTicks;
    
    private double driveInches;
    private double driveTicksTongue = -175;
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
    private final static double STOP_THRESHOLD_ADJUSTED = Robot.DRIVE_SUBSYSTEM.convertInchesToRevs(STOP_THRESHOLD_REAL * ENCODER_TICKS_PER_REVOLUTION);
    
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

    
    private double specificDistancePTongue = 1.0;
    
    private double specificDistanceITongue = 0;
    
    private double specificDistanceDTongue = 0;

    private double specificDistanceFTongue = 0;
    
    public PIDDriveInchesHoldHatch(double inches, boolean reverse) {
        requires(Robot.DRIVE_SUBSYSTEM);
          
//      this.driveTicks = inches / ENCODER_TICKS_PER_REVOLUTION;
    
        if (reverse) {
            this.driveTicks = -Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertInchesToRevs(inches * ENCODER_TICKS_PER_REVOLUTION));//input now has to be ticks instead of revolutions which is why we multiply by 4096
        } else {
            this.driveTicks = Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertInchesToRevs(inches * ENCODER_TICKS_PER_REVOLUTION));
        }
        
        this.driveInches = inches;
    }
    
        
    protected void initialize() {
        
        onTargetCount = 0;
        
        Robot.DRIVE_SUBSYSTEM.resetBothEncoders();
//      Robot.resetNavXAngle();
        Robot.DRIVE_SUBSYSTEM.enablePositionControl();
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kP(pidIdx, specificDistanceP, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kI(pidIdx, specificDistanceI, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kD(pidIdx, specificDistanceD, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kF(pidIdx, specificDistanceF, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kF(pidIdx, specificDistanceF, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.ClearIaccum();
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.ClearIaccum();
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
        
        if (driveInches > 30) {
            Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs); //7500, 20500, 7500, 20000
            Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionAcceleration(20000, timeoutMs); //test 5000
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);
        } else if (driveInches <= 30) {
            Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs); //7500, 15500, 7500, 15000
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
        }

        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, 0);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kP(pidIdx, specificDistancePTongue, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kI(pidIdx, specificDistanceITongue, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kD(pidIdx, specificDistanceDTongue, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kF(pidIdx, specificDistanceFTongue, timeoutMs);

        Robot.HATCH_SUBSYSTEM.hatchTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);

        Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionCruiseVelocity(7500, 10);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionAcceleration(20000, 10);


        Robot.DRIVE_SUBSYSTEM.setPID(driveTicks, driveTicks);
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, driveTicksTongue);
    }
    
    protected void execute() {
    }
    
    @Override
    protected boolean isFinished() {
        double leftPosition = Robot.DRIVE_SUBSYSTEM.getLeftPosition();
        double rightPosition = Robot.DRIVE_SUBSYSTEM.getRightPosition();
        
        if (leftPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && leftPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED) &&
            rightPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && rightPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED)) {
            onTargetCount++;
        } else {
            onTargetCount = 0;
        }
        
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    }
    
    protected void end() {
//        SmartDashboard.putNumber("LEFT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getLeftPosition())));
//        SmartDashboard.putNumber("RIGHT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getRightPosition())));
        OI.distance = Math.abs(Robot.DRIVE_SUBSYSTEM.averageInchesDriven());
//        SmartDashboard.putNumber("Straight", OI.latestDistanceDriven);
        Robot.DRIVE_SUBSYSTEM.enableVBusControl();
        Robot.DRIVE_SUBSYSTEM.resetBothEncoders();
//      Robot.resetNavXAngle();
        Robot.DRIVE_SUBSYSTEM.stop();
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, -50);

    }
    
    protected void interrupted() {
        end();
    }

}