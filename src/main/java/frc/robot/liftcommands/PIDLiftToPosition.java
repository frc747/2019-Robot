package frc.robot.liftcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PIDLiftToPosition extends Command {
    
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
    private double driveTicks;
    
//    private double driveP;
//    private double driveI;
//    private double driveD;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    

    private static final double MAX_PERCENT_VOLTAGE = 1.0; //was 12 (volts previously, now the input is percent)
    private static final double MIN_PERCENT_VOLTAGE = 0.0; //was 1.9 (volts perviously, now the input is percent)

    //STOP_THRESHOLD_REAL was 3 inches and is now 8 inches in an attempt to cut back on time
    
//    private final static int I_ZONE_IN_REVOLUTIONS = 50; //100;
    
    private final static int allowableCloseLoopError = 1;
    
    
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"

    private double specificDistanceP = 0.5;
    
    private double specificDistanceI = 0;
    
    private double specificDistanceD = 0;
    
    private double specificDistanceF = 0;
    
    public PIDLiftToPosition(double position) {
          
//      this.driveTicks = inches / ENCODER_TICKS_PER_REVOLUTION;
            this.driveTicks = position;
    }
    
        
    protected void initialize() {
        
//      Robot.resetNavXAngle();
        Robot.lift.enablePositionControl(Robot.lift.liftPrimary);
        
        Robot.lift.liftPrimary.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.lift.liftPrimary.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.lift.liftPrimary.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.lift.liftPrimary.config_kF(pidIdx, specificDistanceF, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.ClearIaccum();
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.ClearIaccum();
        
        Robot.lift.liftPrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.lift.liftPrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.lift.liftPrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.lift.liftPrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.lift.liftPrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
        
            Robot.lift.liftPrimary.configMotionCruiseVelocity(10000, timeoutMs); //7500, 20500, 7500, 20000
            Robot.lift.liftPrimary.configMotionAcceleration(20000, timeoutMs); //test 5000


        Robot.lift.setPID(driveTicks, driveTicks, Robot.lift.liftPrimary);
    }
    
    protected void execute() {
    }
    
    @Override
    protected boolean isFinished() {
        // double leftPosition = Robot.DRIVE_SUBSYSTEM.getLeftPosition();
        // double rightPosition = Robot.DRIVE_SUBSYSTEM.getRightPosition();
        
        // if (leftPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && leftPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED) &&
        //     rightPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && rightPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED)) {
        //     onTargetCount++;
        // } else {
        //     onTargetCount = 0;
        // }
        
        //return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
        return false;
    }
    
    protected void end() {
//        SmartDashboard.putNumber("LEFT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getLeftPosition())));
//        SmartDashboard.putNumber("RIGHT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getRightPosition())));
//        SmartDashboard.putNumber("Straight", OI.latestDistanceDriven);
        Robot.lift.enableVBusControl(Robot.lift.liftPrimary);
//      Robot.resetNavXAngle();
        Robot.lift.stop(Robot.lift.liftPrimary);
    }
    
    protected void interrupted() {
        end();
    }

}
