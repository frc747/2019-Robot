package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class PIDDartMechanism extends Command {
    
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
    
    private final static int allowableCloseLoopError = 1;
        
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

    private double specificDistanceP = 1.0;
    
    private double specificDistanceI = 0;
    
    private double specificDistanceD = 0;
    
    private double specificDistanceF = 0;
    public PIDDartMechanism(double ticks) {
        requires(Robot.ACTUATOR_SUBSYSTEM);
          
//      this.driveTicks = inches / ENCODER_TICKS_PER_REVOLUTION;
    
      this.driveTicks = ticks;
//        this.driveP = specificDistanceP;
//        this.driveI = specificDistanceI;
//        this.driveD = specificDistanceD;
    }
    
        
    protected void initialize() {
        
        //Robot.DRIVE_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);

//      Robot.resetNavXAngle();
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, 0);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kF(pidIdx, specificDistanceF, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.ClearIaccum();
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.ClearIaccum();
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
        

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionCruiseVelocity(100000, 10);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionAcceleration(100000, 10);

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, driveTicks);
    }
    
    protected void execute() {
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
//        SmartDashboard.putNumber("LEFT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getLeftPosition())));
//        SmartDashboard.putNumber("RIGHT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getRightPosition())));
//        SmartDashboard.putNumber("Straight", OI.latestDistanceDriven);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
        //Robot.DRIVE_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);
//      Robot.resetNavXAngle();
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }

}
