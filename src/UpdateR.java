import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UpdateR extends Thread {
    private final PageTable pageTable;
    private final Lock lock;

    public UpdateR(PageTable pageTable) {
        this.pageTable = pageTable;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(4000); // Sleep for 4 milliseconds
                updateRBits();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateRBits() {
        lock.lock();
        try {
            for (PageTableEntry entry : pageTable.getPageTableEntries()) {
                if (entry != null) {
                    entry.updateRBit();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}

class PageTableEntry {
    private boolean referenced;
    private byte agingBits; 

    public PageTableEntry() {
        this.referenced = false;
        this.agingBits = 0;
    }

    public boolean isReferenced() {
        return referenced;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
    }

    public void updateRBit() {
        agingBits >>= 1;

        if (referenced) {
            agingBits |= 0b10000000;
            referenced = false; 
        }
    }

    public byte getAgingBits() {
        return agingBits;
    }
}

