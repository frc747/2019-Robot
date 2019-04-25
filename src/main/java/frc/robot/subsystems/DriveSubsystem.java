package frc.robot.subsystems;

import frc.robot.commands.ShiftDriveCommand;
// import frc.robot.commands.TankDriveCommand;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

    public TalonSRX leftDrivePrimary = new TalonSRX(1); // 10
    
    public TalonSRX leftDriveMid = new TalonSRX(13);

	public TalonSRX leftDriveBack = new TalonSRX(2); // 9

    public TalonSRX rightDrivePrimary = new TalonSRX(10); // 1
    
    public TalonSRX rightDriveMid = new TalonSRX(14);

    public TalonSRX rightDriveBack = new TalonSRX(9); // 2

    public TalonSRX gearShifter = new TalonSRX(12);

    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;

    private static final double ENCODER_TICKS = 4096;

    private static final double GEAR_RATIO_MULTIPLIER = 1;

    private static final double WHEEL_CIRCUMFERNCE = 20.125;

    public static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;

    public boolean tracking = false;

	StringBuilder sb = new StringBuilder();
	int loops = 0;

    public DriveSubsystem() {
        super();
        gearShifter.setInverted(false);
        gearShifter.setSensorPhase(true);

        leftDrivePrimary.setInverted(true);
        leftDriveMid.setInverted(true);
        leftDriveBack.setInverted(false); //inverted because of the way it is mounted

        rightDrivePrimary.setInverted(false);
        rightDriveMid.setInverted(false);
        rightDriveBack.setInverted(true); //inverted because of the way it is mounted

        leftDriveBack.set(ControlMode.Follower, leftDrivePrimary.getDeviceID());
        leftDriveMid.set(ControlMode.Follower, leftDrivePrimary.getDeviceID());
        rightDriveBack.set(ControlMode.Follower, rightDrivePrimary.getDeviceID());
        rightDriveMid.set(ControlMode.Follower, rightDrivePrimary.getDeviceID());

        leftDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

        rightDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

        gearShifter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

        leftDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        leftDrivePrimary.configMotionAcceleration(20500, timeoutMs);
        rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);

        leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);

        leftDriveMid.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDriveMid.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDriveMid.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        leftDriveMid.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDriveMid.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDriveMid.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDriveMid.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDriveMid.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);

        leftDriveBack.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDriveBack.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDriveBack.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        leftDriveBack.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);


        gearShifter.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        gearShifter.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        gearShifter.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        gearShifter.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ShiftDriveCommand());
        // setDefaultCommand(new TankDriveCommand());
    }

    public void updateSpeeds() {
        leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    }

    public void set(double left, double right) {

        leftDrivePrimary.set(ControlMode.PercentOutput, left);
        rightDrivePrimary.set(ControlMode.PercentOutput, right);
    }

    public void setPID(double leftTicks, double rightTicks) {
        leftDrivePrimary.set(ControlMode.MotionMagic, leftTicks);
        rightDrivePrimary.set(ControlMode.MotionMagic, rightTicks);
    }

    public double convertRevsToInches(double revs) {
        return revs * WHEEL_CIRCUMFERNCE;
    }

    public double convertInchesToRevs(double inches) {
        return inches / WHEEL_CIRCUMFERNCE;
    }

    public double convertRevsToTicks(double revs) {
        return revs * ENCODER_TICKS;
    }

    public double convertTicksToRevs(double ticks) {
        return ticks / ENCODER_TICKS;
    }

    public double convertInchesToTicks(double inches) {
        return convertRevsToTicks(convertInchesToRevs(inches));
    }

    public double convertTicksToInches(double ticks) {
        return convertRevsToInches(convertTicksToRevs(ticks));
    }

    public double applyGearRatio(double original) {
        return original * GEAR_RATIO_MULTIPLIER;
    }

    public double undoGearRatio(double original) {
        return original / GEAR_RATIO_MULTIPLIER;
    }

    public double averageInchesDriven() {
        return convertTicksToInches(undoGearRatio(getCombindedEncoderPosition()));
    }

    public void changeControlMode(ControlMode mode) {
    	leftDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	rightDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
        leftDrivePrimary.set(mode, 0);
        rightDrivePrimary.set(mode, 0);
    }

    public void stop() {
        ControlMode mode = leftDrivePrimary.getControlMode();

        double left = 0;
        double right = 0;

        switch (mode) {
        case MotionMagic:
            left = leftDrivePrimary.getSelectedSensorPosition(pidIdx);
            right = rightDrivePrimary.getSelectedSensorPosition(pidIdx);
            break;
        case PercentOutput:
        case Velocity:
        case Follower:
        default:
            // Values should be 0;
            break;
        }

        this.set(left, right);
    }

    public void changeDriveBrakeMode(boolean enabled) {
        if (enabled) {
          leftDrivePrimary.setNeutralMode(NeutralMode.Brake);
          leftDriveMid.setNeutralMode(NeutralMode.Brake);
          leftDriveBack.setNeutralMode(NeutralMode.Brake);
          rightDrivePrimary.setNeutralMode(NeutralMode.Brake);
          rightDriveMid.setNeutralMode(NeutralMode.Brake);
          rightDriveBack.setNeutralMode(NeutralMode.Brake);
        } else {
            leftDrivePrimary.setNeutralMode(NeutralMode.Coast);
            leftDriveMid.setNeutralMode(NeutralMode.Coast);
            leftDriveBack.setNeutralMode(NeutralMode.Coast);
            rightDrivePrimary.setNeutralMode(NeutralMode.Coast);
            rightDriveMid.setNeutralMode(NeutralMode.Coast);
            rightDriveBack.setNeutralMode(NeutralMode.Coast);
        }
    
      }

    public void enablePositionControl() {
        this.changeControlMode(ControlMode.MotionMagic);
    }

    public void enableVBusControl() {
        this.changeControlMode(ControlMode.PercentOutput);
    }

    public void resetLeftEncoder() {
        this.enableVBusControl();
        leftDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void resetRightEncoder() {
        this.enableVBusControl();
        rightDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void resetBothEncoders(){
        this.enableVBusControl();
    	this.rightDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	this.leftDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
        //get the current encoder position regardless of whether it is the current feedback device
    public double getLeftEncoderPosition() {
        return leftDrivePrimary.getSelectedSensorPosition(pidIdx);
    }

    public double getRightEncoderPosition() {
        return rightDrivePrimary.getSelectedSensorPosition(pidIdx);
    }

    public double getLeftPosition() {
        return leftDrivePrimary.getSelectedSensorPosition(pidIdx);
    }

    public double getRightPosition() {
        return rightDrivePrimary.getSelectedSensorPosition(pidIdx);
    }

    public double getCombindedEncoderPosition() {
        return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
    }

}
