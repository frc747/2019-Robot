package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.PIDHatchMechanism;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class HatchSubsystem extends Subsystem {

  public TalonSRX hatchTalon = new TalonSRX(5);


  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  public static double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public HatchSubsystem() {
    super();

    hatchTalon.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

    hatchTalon.setInverted(true);
    hatchTalon.setSensorPhase(true);

    hatchTalon.configMotionCruiseVelocity(7500, 10);
    hatchTalon.configMotionAcceleration(20000, 10);

    hatchTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    hatchTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    hatchTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    hatchTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PIDHatchMechanism(-50, false));
  }
}
