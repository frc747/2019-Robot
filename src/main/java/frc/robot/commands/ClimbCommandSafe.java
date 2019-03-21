package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class ClimbCommandSafe extends Command {
    
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
    private double driveTicks = 8192; // FIND VALUE FOR PIN DISENGAGED
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    private int shiftCount = 0;
    private boolean shiftHigh = false;
    private final static double ENCODER_TICKS_PER_REVOLUTION = 4096;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;
    
    private final static int allowableCloseLoopError = 1;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

    private double specificDistanceP = 1.0;
    
    private int LEFT_BUMPER = 5;
    private int RIGHT_BUMPER = 6;

    private double specificDistanceI = 0;
    
    private double specificDistanceD = 0;

    private double specificDistanceF = 1.5;
    
    private boolean latchInPosition = false;

    private double leftValue;

    private double rightValue;

    private double shifterValue;

    public ClimbCommandSafe() {
        requires(Robot.climb);
    }
    
        
    protected void initialize() {
        latchInPosition = false;
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
          
        SmartDashboard.putBoolean("Latch in postition?", latchInPosition);

        //if (OI.leftStick.getRawButton(9)) {
        if (OI.operatorController.getRawButton(RIGHT_BUMPER) && !latchInPosition) {
            // Robot.climb.latch.set(ControlMode.PercentOutput, shifterValue);
            if (Robot.climb.latch.getSelectedSensorPosition() > driveTicks - 200 && Robot.climb.latch.getSelectedSensorPosition() < driveTicks + 1000) {
                Robot.climb.latch.set(ControlMode.PercentOutput, 0);
                latchInPosition = true;
            } else {
                Robot.climb.latch.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
                Robot.climb.latch.config_kI(pidIdx, specificDistanceI, timeoutMs);
                
                Robot.climb.latch.config_kD(pidIdx, specificDistanceD, timeoutMs);
                
                Robot.climb.latch.config_kF(pidIdx, specificDistanceF, timeoutMs);
                Robot.climb.latch.set(ControlMode.MotionMagic, driveTicks);
            }

        
        }

        if(OI.operatorController.getRawButton(LEFT_BUMPER) && latchInPosition) {
            double winchValue = Math.abs(OI.operatorController.getRawAxis(1));
            Robot.climb.setWinches(-winchValue);
        } else {
            Robot.climb.setWinches(0.0);
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