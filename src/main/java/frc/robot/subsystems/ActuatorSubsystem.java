/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DartDriveCommand;
import frc.robot.commands.PIDDartMechanism;

/**
 * Add your docs here.
 */
public class ActuatorSubsystem extends Subsystem {

  public TalonSRX dartTalon = new TalonSRX(7);


  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  private static final double ENCODER_TICKS = 4096;

  private static final double GEAR_RATIO_MULTIPLIER = 1;
  //Gear ratio, motor needs to rotate 5.4 times more to achieve one actual rotation
  // 4096 for the mag encoders

  private static final double WHEEL_CIRCUMFERNCE = 20.125;  

  public static double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public ActuatorSubsystem() {
    super();

    dartTalon.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

    dartTalon.configMotionCruiseVelocity(150000, 10);
    dartTalon.configMotionAcceleration(150000, 10);

    dartTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    dartTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    dartTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    dartTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);


  }

  @Override
  public void initDefaultCommand() {
        setDefaultCommand(new PIDDartMechanism(0));
  }
}
