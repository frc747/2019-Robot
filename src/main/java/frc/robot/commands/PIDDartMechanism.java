package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class PIDDartMechanism extends Command {
        
    private double driveTicks;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    
    private final static double ENCODER_TICKS_PER_REVOLUTION = 4096;

    private static final double MAX_PERCENT_VOLTAGE = .5; //regular current draw is ~20 AMPS
    private static final double MIN_PERCENT_VOLTAGE = 0.0;
    
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
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.enableCurrentLimit(true);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configContinuousCurrentLimit(30);

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
        

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionCruiseVelocity(50000, 10);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionAcceleration(50000, 10);

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, driveTicks);
    }
    
    protected void execute() {
        SmartDashboard.putNumber("Dart Current Value:", Robot.ACTUATOR_SUBSYSTEM.dartTalon.getOutputCurrent());

        // logic is made redundant by "configContinuousCurrentLimit()"

        // double dartPosition = Robot.ACTUATOR_SUBSYSTEM.getDartPosition();
        // if (this.driveTicks != 0 && dartPosition < (-221740 + 40000) && dartPosition > (-221740 - 5000)) {
        //     if (Robot.ACTUATOR_SUBSYSTEM.dartTalon.getOutputCurrent() < 29.5) {
        //         Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, driveTicks);
        //     } else {
        //         Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
        //     }
        // } else {
        //     if (Robot.ACTUATOR_SUBSYSTEM.dartTalon.getOutputCurrent() < 29.5) {
        //         Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, driveTicks);
        //     } else {
        //         Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
        //     }
        // }
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
//        SmartDashboard.putNumber("LEFT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getLeftPosition())));
//        SmartDashboard.putNumber("RIGHT FINAL Drive Distance: Inches", Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertRevsToInches(Robot.DRIVE_SUBSYSTEM.getRightPosition())));
//        SmartDashboard.putNumber("Straight", OI.latestDistanceDriven);
        //Robot.DRIVE_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);
//      Robot.resetNavXAngle();
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }

}
