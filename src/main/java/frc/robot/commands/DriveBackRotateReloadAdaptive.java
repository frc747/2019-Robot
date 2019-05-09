/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveBackRotateReloadAdaptive extends Command {
  private double driveTicks;
  private double driveInches;
  
  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  private static final int slotIdx = 0;
  
  private final static double ENCODER_TICKS_PER_REVOLUTION = 4096;

  private static final double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  private final static double STOP_THRESHOLD_REAL = 3;
  private final static double STOP_THRESHOLD_ADJUSTED = Robot.DRIVE_SUBSYSTEM.convertInchesToRevs(STOP_THRESHOLD_REAL * ENCODER_TICKS_PER_REVOLUTION);
  
  private final static int allowableCloseLoopError = 1;
  
  private int onTargetCount = 0;
  
  private final static int TARGET_COUNT_ONE_SECOND = 50;
  
  //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
  private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

  private double driveP = 0.5;
  
  private double driveI = 0;
  
  private double driveD = .1;
  
  private double driveF = .199;
  public DriveBackRotateReloadAdaptive() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    driveInches = 0;
    // rotateDegrees = 0;
    if (!Robot.isTeleop) {

      if (Robot.autoSideFaceCargoShip) {
        if (Robot.autoSideLeft) {
          driveInches = 46;
          // rotateDegrees = 103;

          // addSequential(new PIDDriveInches(46, true));
          // addSequential(new PIDDriveRotateCustom(103, true));
        } else if (Robot.autoSideRight) {
          driveInches = 46;
          // rotateDegrees = -103;

          // addSequential(new PIDDriveInches(46, true));
          // addSequential(new PIDDriveRotateCustom(-103, true));
        }


      } else if (Robot.autoRocket) {
        if (Robot.autoSideLeft) {
          driveInches = 15;
          // rotateDegrees = -150;

          // addSequential(new PIDDriveInches(15, true));
          // addSequential(new PIDDriveRotateCustom(-150, true));
        } else if (Robot.autoSideRight) {
          driveInches = 15;
          // rotateDegrees = 150;

          // addSequential(new PIDDriveInches(15, true));
          // addSequential(new PIDDriveRotateCustom(150, true));
        }


      } else if (Robot.autoFrontFaceCargoShip) {
        if (Robot.autoSideLeft) {
          driveInches = 10;
          // rotateDegrees = -143;

          // addSequential(new PIDDriveInches(10, true));
          // addSequential(new PIDDriveRotateCustom(-143, true));
        } else if (Robot.autoSideRight) {
          driveInches = 10;
          // rotateDegrees = 143;

          // addSequential(new PIDDriveInches(10, true));
          // addSequential(new PIDDriveRotateCustom(143, true));
        }
      }
    }
    
    // made negative because this command will only want to drive backwards
    // this.driveTicks = -Robot.DRIVE_SUBSYSTEM.applyGearRatio(Robot.DRIVE_SUBSYSTEM.convertInchesToRevs(inches * ENCODER_TICKS_PER_REVOLUTION));//input now has to be ticks instead of revolutions which is why we multiply by 4096

    onTargetCount = 0;
        
    Robot.DRIVE_SUBSYSTEM.resetBothEncoders();

    Robot.DRIVE_SUBSYSTEM.enablePositionControl();
    
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
    
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
    
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
    
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
    
    
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
            
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
            
    if (driveInches > 30) {
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionAcceleration(20000, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);
    } else if (driveInches <= 30) {
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
    }

    Robot.DRIVE_SUBSYSTEM.setPID(driveTicks, driveTicks);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
