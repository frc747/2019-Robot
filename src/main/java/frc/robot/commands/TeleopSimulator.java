package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
public class TeleopSimulator extends Command {
        
    private double driveTicksGear = 830;
    private double driveTicksTongue = 850;
    private double driveTicksDart = -221740;

    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    private int shiftCount = 0;
    private boolean shiftHigh = false;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;
    
    private final static int allowableCloseLoopError = 1;
    
    double speed = 0.50;
    double rampDown = 1;
    double rate;    
    double last;

    private boolean firstVisionCheckDone;

    private double specificDistancePGear = 1.0;
    
    private double specificDistanceIGear = 0;
    
    private double specificDistanceDGear = 0;

    private double specificDistanceFGear = 1.5;




    private double specificDistancePTongue = 1.0;
    
    private double specificDistanceITongue = 0;
    
    private double specificDistanceDTongue = 0;

    private double specificDistanceFTongue = 0;



    private double specificDistancePDart = 1.0;
    
    private double specificDistanceIDart = 0;
    
    private double specificDistanceDDart = 0;

    private double specificDistanceFDart = 0;
    
    private double leftValue;

    private double rightValue;

    public TeleopSimulator() {
        // requires(Robot.DRIVE_SUBSYSTEM);
        // requires(Robot.ACTUATOR_SUBSYSTEM);
        // requires(Robot.HATCH_SUBSYSTEM);

    }
    
        
    protected void initialize() {
        SmartDashboard.putBoolean("Ready to Drive", true);
        //finished = false;
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

        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kP(pidIdx, specificDistancePGear, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kI(pidIdx, specificDistanceIGear, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kD(pidIdx, specificDistanceDGear, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.gearShifter.config_kF(pidIdx, specificDistanceFGear, timeoutMs);

        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, 0);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kP(pidIdx, specificDistancePTongue, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kI(pidIdx, specificDistanceITongue, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kD(pidIdx, specificDistanceDTongue, timeoutMs);
        
        Robot.HATCH_SUBSYSTEM.hatchTalon.config_kF(pidIdx, specificDistanceFTongue, timeoutMs);
        
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

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, 0);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kP(pidIdx, specificDistancePDart, timeoutMs);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kI(pidIdx, specificDistanceIDart, timeoutMs);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kD(pidIdx, specificDistanceDDart, timeoutMs);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kF(pidIdx, specificDistanceFDart, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.ClearIaccum();
// //        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.ClearIaccum();
//         Robot.ACTUATOR_SUBSYSTEM.dartTalon.enableCurrentLimit(true);
//         Robot.ACTUATOR_SUBSYSTEM.dartTalon.
//         //Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakCurrentDuration(4);
//         Robot.ACTUATOR_SUBSYSTEM.dartTalon.configContinuousCurrentLimit(0);
//         Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakCurrentLimit(30);
//         Robot.ACTUATOR_SUBSYSTEM.dartTalon.enableCurrentLimit(true);
         Robot.ACTUATOR_SUBSYSTEM.dartTalon.enableCurrentLimit(false);

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputForward(+.6, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputReverse(-.6, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
//        Robot.DRIVE_SUBSYSTEM.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
//        Robot.DRIVE_SUBSYSTEM.talonDriveRightPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);
        

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionCruiseVelocity(50000, 10);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionAcceleration(50000, 10);

        firstVisionCheckDone = false;
    }
    
    protected void execute() {
        leftValue = -OI.leftStick.getRawAxis(1);
        rightValue = -OI.rightStick.getRawAxis(1);

        if (Math.abs(leftValue) < 0.1) {
            leftValue = 0;
        }
        if (Math.abs(rightValue) < 0.1) {
            rightValue = 0;
        }
        if(OI.operatorController.getRawButton(7)) {
            if(firstVisionCheckDone == false) {
                updateTrackValues();
                firstVisionCheckDone = true;
            }
            double adjustMagnitiude;
            if(speed < .45) {
                adjustMagnitiude = 4.5;
                speed = .45;
                rate = 0;
            } else {
                adjustMagnitiude = 3.25;
            }
            if(OI.leftStick.getRawButton(10)) {
                leftValue = -OI.leftStick.getRawAxis(1);
                rightValue = -OI.rightStick.getRawAxis(1);
      
                if (Math.abs(leftValue) < 0.1) {
                    leftValue = 0;
                }
                if (Math.abs(rightValue) < 0.1) {
                    rightValue = 0;
                }

                Robot.DRIVE_SUBSYSTEM.set(leftValue, rightValue);
            } else {

                if(rampDown > .4) {
                    rampDown -= rate;
                }
 
                SmartDashboard.putNumber("rampdown", rampDown);

                leftValue = ((speed) + ((.75*(Math.tanh(OI.x/5)))/adjustMagnitiude))*rampDown;
                rightValue = (-((speed) - ((.75*(Math.tanh(OI.x/5)))/adjustMagnitiude))*rampDown);

                Robot.DRIVE_SUBSYSTEM.set(leftValue, -rightValue);
            } 
        } else {
            Robot.DRIVE_SUBSYSTEM.set(leftValue, rightValue);

        }
        // check if the robot should be considered moving towards high gear or stay in low gear
        if((leftValue > .9 && rightValue > .9) || (leftValue < -.9 && rightValue < -.9) && Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() > 1600 || Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() < -1600) {
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
            if (Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() > driveTicksGear - 10 && Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() < driveTicksGear + 10) {
                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
            } else {
                Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionCruiseVelocity(7500, 10); //1500
                Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionAcceleration(20000, 10); //2000

                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.MotionMagic, driveTicksGear);
            }

        } else {
            // Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
            if (Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() > -10 && Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() < 10) {
                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
            } else {
              
                Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.MotionMagic, 0);
            }
        }





        if (OI.operatorController.getRawButton(2)) {
            if (Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition() > driveTicksTongue - 10 && Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition() < driveTicksTongue + 10) {
                    Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.PercentOutput, 0);
                } else {
                    Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionCruiseVelocity(7500, 10); //1500
                    Robot.HATCH_SUBSYSTEM.hatchTalon.configMotionAcceleration(20000, 10); //2000

                    Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, driveTicksTongue);
                }
    
            } else {
                if (Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition() > -10 && Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition() < 10) {
                    Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.PercentOutput, 0);
                } else {

                    Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.MotionMagic, 0);
                }
        }





        if (OI.operatorController.getRawButton(4)) {
            if (Robot.ACTUATOR_SUBSYSTEM.dartTalon.getSelectedSensorPosition() > driveTicksDart - 10 && Robot.ACTUATOR_SUBSYSTEM.dartTalon.getSelectedSensorPosition() < driveTicksDart + 10) {
                Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
            } else {
                Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionCruiseVelocity(7500, 10); //1500
                Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionAcceleration(20000, 10); //2000

                Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, driveTicksDart);
            }
            
        } else {
            if (Robot.ACTUATOR_SUBSYSTEM.dartTalon.getSelectedSensorPosition() > -10 && Robot.ACTUATOR_SUBSYSTEM.dartTalon.getSelectedSensorPosition() < 10) {
                Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
            } else {

                Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, 0);
            }
        }

        if(OI.operatorController.getRawButton(6)) {
            SmartDashboard.putBoolean("Ready to Drive", false);
            // finished = true;
        } else {
            SmartDashboard.putBoolean("Ready to Drive", true);
        }
    }
    
    

    @Override
    protected boolean isFinished() {
        return OI.operatorController.getRawButton(6);
    }
    
    protected void end() {
        //finished = false;
        Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
        Robot.HATCH_SUBSYSTEM.hatchTalon.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }

    public void updateTrackValues() {
        rampDown = 1;

        if(OI.y == 0) {
          speed = .25;
          rate = 0;
        } else {
          speed = (1/OI.y)*5;
          rate = .009;
        }

        if(speed > 1) {
            speed = 1.0;
        }

    
    
        System.out.println("line");

        OI.table.getEntry("pipeline").setDouble(0);
    }

}