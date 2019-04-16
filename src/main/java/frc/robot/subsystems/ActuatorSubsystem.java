package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.PIDDartMechanism;

public class ActuatorSubsystem extends Subsystem {

  public TalonSRX dartTalon = new TalonSRX(6);


  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  public static double MAX_PERCENT_VOLTAGE = .6;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public ActuatorSubsystem() {
    super();

    dartTalon.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

    dartTalon.configMotionCruiseVelocity(50000, 10);
    dartTalon.configMotionAcceleration(50000, 10);

    dartTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    dartTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    dartTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    dartTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    

  }

  @Override
  public void initDefaultCommand() {
        setDefaultCommand(new PIDDartMechanism(0)); // 0 original
  }

  public double getDartPosition() {
    return dartTalon.getSelectedSensorPosition(pidIdx);
  }
}
