package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class ShiftDriveCommand extends Command {
    
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
    private double driveTicks = 830;
    
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
    
    private double specificDistanceI = 0;
    
    private double specificDistanceD = 0;

    private double specificDistanceF = 1.5;
    
    private double leftValue;

    private double rightValue;

    private double shifterValue;

    public ShiftDriveCommand() {
        requires(Robot.DRIVE_SUBSYSTEM);
    }
    
        
    protected void initialize() {
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.gearShifter.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.gearShifter.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.gearShifter.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.gearShifter.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.gearShifter.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
             
        Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionCruiseVelocity(7500, 10); //1500
        Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionAcceleration(20000, 10); //2000

        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kF(pidIdx, specificDistanceF, timeoutMs);
    }
    
    protected void execute() {
        leftValue = -OI.leftStick.getRawAxis(1);
        rightValue = -OI.rightStick.getRawAxis(1);
        shifterValue = OI.operatorController.getRawAxis(5);

        if (Math.abs(leftValue) < 0.1) {
            leftValue = 0;
        }
        if (Math.abs(rightValue) < 0.1) {
            rightValue = 0;
        }
    
        Robot.DRIVE_SUBSYSTEM.set(leftValue, rightValue);

        // check if the robot should be considered moving towards high gear or stay in low gear
        if((leftValue > .9 && rightValue > .9) || (leftValue < -.9 && rightValue < -.9) && (Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() > 1600 || Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() < -1600)) {
            shiftCount++;
        } else {
            shiftCount = 0;
        }
      


        //   // if shift count has been adding for half a second
        if(shiftCount > 25) {
            shiftHigh = true;
        } else {
            shiftHigh = false;
        }
        SmartDashboard.putBoolean("HIGH GEAR?: ", shiftHigh);
          
        if (shiftHigh) {
        //if (OI.leftStick.getRawButton(9)) {
            // Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, shifterValue);
            if (Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() > driveTicks - 10 && Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() < driveTicks + 10) {
                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
            } else {
                Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionCruiseVelocity(7500, 10); //1500
                Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionAcceleration(20000, 10); //2000
                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.MotionMagic, driveTicks);
            }

        } else {
            // Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
            if (Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() > -10 && Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() < 10) {
                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
            } else {
                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.MotionMagic, 0);
            }
        }
    }
    
    @Override
    protected boolean isFinished() {
         return false;
    }
    
    protected void end() {
        Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }

}