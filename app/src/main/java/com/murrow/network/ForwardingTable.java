package com.murrow.network;

import android.util.Log;

import com.murrow.support.NetworkConstants;
import com.murrow.support.Utilities;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Corbin Murrow on 3/6/2016.
 */
public class ForwardingTable extends RouteTable
{
    public ForwardingTable()
    {
        super();
    }

    public void addFibEntry(RouteTableEntry rte)
    {
        Iterator<RouteTableEntry> it = table.iterator();
        boolean found = false;
        RouteTableEntry tmp;

        NetworkDistancePair rteNDP = rte.getNetworkDistancePair();

        while (it.hasNext() && !found)
        {
            tmp = it.next();
            NetworkDistancePair tmpNDP = tmp.getNetworkDistancePair();

            if (tmpNDP.getNetwork().equals(rteNDP.getNetwork()) )
            {
                found = true;
                if (rteNDP.getDistance() < rteNDP.getDistance())
                {
                    table.remove(tmp);
                    table.add(rte);
                    Log.i("Forwarding Table", "Added " + Integer.toHexString(rte.getSourceLL3P()) + " to forwarding table.");
                }
            }
        }

        if (!found)
        {
            table.add(rte);
            Log.i("Forwarding Table", "Added " + Integer.toHexString(rte.getSourceLL3P()) + " to forwarding table.");
        }
    }

    public void addRouteList(ArrayList<RouteTableEntry> list)
    {
        for (RouteTableEntry rte : list)
        {
            addFibEntry(rte);
        }
    }

    public Integer getNextHop(Integer LL3PAddr)
    {
        Integer network = Utilities.getNetworkFromInteger(LL3PAddr);

        Iterator<RouteTableEntry> it = table.iterator();
        RouteTableEntry tmp;

        while (it.hasNext())
        {
            tmp = it.next();

            if (tmp.getNetworkDistancePair().getNetwork().equals(network))
            {
                return tmp.getNextHop();
            }
        }

        return null;
    }

    public ArrayList<RouteTableEntry> getFIBExcludingLL3PAddr(Integer LL3PAddr)
    {
        ArrayList<RouteTableEntry> list = new ArrayList<>();
        list.addAll(table);

        Integer network = Utilities.getNetworkFromInteger(LL3PAddr);

        for(RouteTableEntry rte : list)
        {
            if (rte.getNetworkDistancePair().getNetwork().equals(network))
                list.remove(rte);
        }

        return list;
    }

    public void reset()
    {
        table.clear();
    }
}
