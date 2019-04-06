package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ClimbCommandSafe extends Command {
        
    private double driveTicks = 8192; // FIND VALUE FOR PIN DISENGAGED
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;
    
    private final static int allowableCloseLoopError = 1;
    
    private int LEFT_BUMPER = 5;
    private int RIGHT_BUMPER = 6;

    private double specificDistanceP = 1.0;

    private double specificDistanceI = 0;
    
    private double specificDistanceD = 0;

    private double specificDistanceF = 1.5;

    public ClimbCommandSafe() {
        requires(Robot.climb);
    }
    
        
    protected void initialize() {
        OI.latchInPosition = false;
        Robot.climb.latch.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.climb.latch.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.climb.latch.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.climb.latch.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
        Robot.climb.latch.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
             
        Robot.climb.latch.configMotionCruiseVelocity(1500, 10);
        Robot.climb.latch.configMotionAcceleration(2000, 10);

        Robot.climb.latch.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.climb.latch.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.climb.latch.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.climb.latch.config_kF(pidIdx, specificDistanceF, timeoutMs);
    }
    
    protected void execute() {

        if (OI.operatorController.getRawButton(RIGHT_BUMPER) && !OI.latchInPosition) {
            if (Robot.climb.latch.getSelectedSensorPosition() > driveTicks - 200 && Robot.climb.latch.getSelectedSensorPosition() < driveTicks + 1000) {
                Robot.climb.latch.set(ControlMode.PercentOutput, 0);

                OI.latchInPosition = true;
            } else {
                Robot.climb.latch.config_kP(pidIdx, specificDistanceP, timeoutMs);
                Robot.climb.latch.config_kI(pidIdx, specificDistanceI, timeoutMs);
                Robot.climb.latch.config_kD(pidIdx, specificDistanceD, timeoutMs);
                Robot.climb.latch.config_kF(pidIdx, specificDistanceF, timeoutMs);

                Robot.climb.latch.set(ControlMode.MotionMagic, driveTicks);
            }
        }

        if(OI.operatorController.getRawButton(LEFT_BUMPER) && OI.latchInPosition) {
            double winchValue = Math.abs(OI.operatorController.getRawAxis(1));

            if (winchValue > 0.4) {
                winchValue = 0.4;
            }

            Robot.climb.setClimb(winchValue);
        } else {
            Robot.climb.setClimb(0.0);
        }
    }
    
    @Override
    protected boolean isFinished() {
         return false;
    }
    
    protected void end() {
        Robot.climb.latch.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }
}