/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.PIDHatchMechanism;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * Add your docs here.
 */
public class HatchSubsystem extends Subsystem {

  public TalonSRX hatchTalon = new TalonSRX(6);


  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  private static final double ENCODER_TICKS = 4096;

  private static final double GEAR_RATIO_MULTIPLIER = 1;
  //Gear ratio, motor needs to rotate 5.4 times more to achieve one actual rotation
  // 4096 for the mag encoders

  private static final double WHEEL_CIRCUMFERNCE = 20.125;

  public static double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public HatchSubsystem() {
    super();

    hatchTalon.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

    hatchTalon.configMotionCruiseVelocity(7500, 10);
    hatchTalon.configMotionAcceleration(20000, 10);

    hatchTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    hatchTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    hatchTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    hatchTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);


  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PIDHatchMechanism(0, false));
  }
}
