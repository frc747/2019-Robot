package frc.robot.subsystems;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.TankDriveCommand;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    
    public TalonSRX leftDrivePrimary = new TalonSRX(8);

	public TalonSRX leftDriveBack = new TalonSRX(9);

	public TalonSRX rightDrivePrimary = new TalonSRX(1);

    public TalonSRX rightDriveBack = new TalonSRX(2);
    

    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    
    private static final double ENCODER_TICKS = 4096;
    
    private static final double GEAR_RATIO_MULTIPLIER = 1; 
    //Gear ratio, motor needs to rotate 5.4 times more to achieve one actual rotation
    // 4096 for the mag encoders
    
    private static final double WHEEL_CIRCUMFERNCE = 20.125;
    
    public static double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;
  
    //Gear Distance IN REVOLUTIONS 3.7125 (needed like another inch or so; trying 3.725
    
//    private static final double TICKS_PER_INCH = ENCODER_TICKS / WHEEL_CIRCUMFERNCE;
    
	StringBuilder sb = new StringBuilder();
	int loops = 0;
    
    public DriveSubsystem() {
        super();
        
        leftDrivePrimary.setInverted(true);
        leftDriveBack.setInverted(true);
        
        rightDriveBack.setInverted(false);
        rightDrivePrimary.setInverted(false);
       
        leftDriveBack.set(ControlMode.Follower, leftDrivePrimary.getDeviceID());
        rightDriveBack.set(ControlMode.Follower, rightDrivePrimary.getDeviceID());
        
        leftDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

        rightDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);
        
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
        
    }
   
    public void initDefaultCommand() {
        setDefaultCommand(new TankDriveCommand());
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
