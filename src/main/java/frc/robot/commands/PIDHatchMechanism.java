package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class PIDHatchMechanism extends Command {
        
    private double driveTicks;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;

    private final static int allowableCloseLoopError = 1;
    
    private double driveHatchP = 1.0;
    
    private double driveHatchI = 0;
    
    private double driveHatchD = 0;
    
    private double driveHatchF = 0;
    
    public PIDHatchMechanism(double ticks, boolean reverse) {
        requires(Robot.HATCH_SUBSYSTEM);    
        this.driveTicks = ticks;
    }
    
        
    protected void initialize() {
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, 0);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kP(pidIdx, driveHatchP, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kI(pidIdx, driveHatchI, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kD(pidIdx, driveHatchD, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kF(pidIdx, driveHatchF, timeoutMs);
        
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionCruiseVelocity(7500, 10);
        Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionAcceleration(20000, 10);

        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, driveTicks);
    }
    
    protected void execute() {
        if (Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition() > 500 || (!Robot.operatorControl && Robot.isAutonomous)) {
            OI.tongueIsOut = true;
        } else {
            OI.tongueIsOut = false;
        }
    }
    
    @Override
    protected boolean isFinished() {
         return false;
    }
    
    protected void end() {
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }

}
