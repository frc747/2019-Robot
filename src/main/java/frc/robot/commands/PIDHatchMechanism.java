package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class PIDHatchMechanism extends Command {
    
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

    private static final double MAX_PERCENT_VOLTAGE = .25; //was 12 (volts previously, now the input is percent)
    private static final double MIN_PERCENT_VOLTAGE = 0.0; //was 1.9 (volts perviously, now the input is percent)

    //STOP_THRESHOLD_REAL was 3 inches and is now 8 inches in an attempt to cut back on time
    private final static double STOP_THRESHOLD_REAL = 3; //3.0;
    private final static double STOP_THRESHOLD_ADJUSTED = 10;
    
//    private final static int I_ZONE_IN_REVOLUTIONS = 50; //100;
    
    private final static int allowableCloseLoopError = 1;
    
    private int onTargetCount = 0;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

    private double specificDistanceP = 1.0;
    
    private double specificDistanceI = 0;
    
    private double specificDistanceD = 0;
    
    private double specificDistanceF = 0;
    
    public PIDHatchMechanism(double ticks, boolean reverse) {
        requires(Robot.HATCH_SUBSYSTEM);
          
//      this.driveTicks = inches / ENCODER_TICKS_PER_REVOLUTION;
    
      this.driveTicks = ticks;
//        this.driveP = specificDistanceP;
//        this.driveI = specificDistanceI;
//        this.driveD = specificDistanceD;
    }
    
        
    protected void initialize() {
        
        onTargetCount = 0;
        
        //Robot.DRIVE_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);

//      Robot.resetNavXAngle();
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, 0);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kF(pidIdx, specificDistanceF, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.ClearIaccum();
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.ClearIaccum();
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
        

        Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionCruiseVelocity(7500, 10);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionAcceleration(20000, 10);

        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, driveTicks);
    }
    
    protected void execute() {
    }
    
    @Override
    protected boolean isFinished() {
        /*double hatchPosition = Robot.DRIVE_SUBSYSTEM.hatchTalon.getSelectedSensorPosition();
        
        if (hatchPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && hatchPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED)) {
            onTargetCount++;
        } else {
            onTargetCount = 0;
        }
        
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);*/
        return false;
    }
    
    protected void end() {
//        SmartDashboard.putNumber("LEFT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getLeftPosition())));
//        SmartDashboard.putNumber("RIGHT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getRightPosition())));
//        SmartDashboard.putNumber("Straight", OI.latestDistanceDriven);
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.PercentOutput, 0);
        //Robot.DRIVE_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);
//      Robot.resetNavXAngle();
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }

}
