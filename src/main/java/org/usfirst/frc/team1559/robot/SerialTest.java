package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.hal.util.*; 

public class SerialTest {

    public SPI port;
    private byte[] getFeatures = {(byte)0xae, (byte)0xc1, (byte)0x30, (byte)0x02, (byte)0x00, (byte)0x07, 0, 0};

    public SerialTest() {
        port = new SPI(SPI.Port.kMXP);//prolly wrong

        port.setMSBFirst();
        port.setChipSelectActiveLow();
        port.setClockRate(1000);
        port.setSampleDataOnFalling();
        port.setClockActiveLow();
    }

    public void start() {
    }

    public byte read() {
        byte[] returned = new byte[8];
        port.transaction(getFeatures, returned, 8);
        return returned[6];
    }
}