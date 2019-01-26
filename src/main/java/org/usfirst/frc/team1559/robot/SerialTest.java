package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team1559.robot.subsystems.pixylinevector;
import java.util.Arrays;

public class SerialTest {

    public SPI port;
    private byte[] getFeatures = {(byte)0xae, (byte)0xc1, (byte)0x30, (byte)0x02, (byte)0x00, (byte)0x07, 0, 0};
    private byte[] lampon = {(byte)0xae, (byte)0xc1, (byte)0x16, (byte)0x02, (byte)0x01, (byte)0x01, 0, 0};
	public Object v;

    public SerialTest() {
        port = new SPI(SPI.Port.kOnboardCS1);

        port.setMSBFirst();
        port.setChipSelectActiveLow();
        port.setClockRate(1000);
        port.setSampleDataOnTrailingEdge();
        port.setClockActiveLow();
    }

    public void start() {
    }

    public byte lampon() {
        byte[] returned = new byte[10];
        port.transaction(lampon, returned, 6);
        return returned[6];
                
    }
    public pixylinevector getvector() {
        byte[] returned = new byte[16];
        port.write(getFeatures, 8);
    

        port.read(false, returned, 6);
        port.read(false, returned, 16);
         var v=new pixylinevector();
        // if (returned[2]==49 && returned[7]>0){
          //   v.valid=true;
            v.x0=returned[10];
            v.y0=returned[11];
            v.x1=returned[12];
            v.y1=returned[13];
            v.index=returned[14];
            v.flags=returned[15];
            System.out.println(Arrays.toString(returned));
        
        //}
       //  else{
            //v.valid=false;
        //}
        return v;

    }
}