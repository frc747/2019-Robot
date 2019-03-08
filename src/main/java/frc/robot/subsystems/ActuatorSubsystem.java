/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.PIDDartMechanism;

/**
 * Add your docs here.
 */
public class ActuatorSubsystem extends Subsystem {

  public TalonSRX dartTalon = new TalonSRX(6);


  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  public static double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public ActuatorSubsystem() {
    super();

    dartTalon.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

    dartTalon.configMotionCruiseVelocity(100000, 10);
    dartTalon.configMotionAcceleration(100000, 10);

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