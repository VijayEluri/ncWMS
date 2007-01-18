/*
 * Copyright (c) 2006 The University of Reading
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University of Reading, nor the names of the
 *    authors or contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package uk.ac.rdg.resc.ncwms.datareader;

import java.io.IOException;
import org.apache.log4j.Logger;
import thredds.ui.SuperComboBox;
import ucar.nc2.NetcdfFile;
import ucar.nc2.dataset.CoordSysBuilder;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.util.CancelTask;

/**
 * Class that builds a coordinate system for NEMO data
 *
 * @author Jon Blower
     * $Revision$
 * $Date$
 * $Log$
 */
public class NemoCoordSysBuilder extends CoordSysBuilder
{
    private static final Logger logger = Logger.getLogger(NemoCoordSysBuilder.class);
    
    /**
     * Static method that returns true if the given NetcdfFile is recognised
     * by this coord sys builder.  This is an alternative to adding a "Conventions"
     * global attribute to the NetCDF file or NCML.
     */
    public static boolean isMine(NetcdfFile ncFile)
    {
        logger.debug("Checking file {} ...", ncFile.getLocation());
        boolean isMine = ncFile.getLocation().contains("NEMO");
        logger.debug("... returning {}", isMine);
        return isMine;
    }

    public void setConventionUsed(String string)
    {
        logger.debug("Called dgsdfg sdfgsfdg setConventionUsed({})", string);
    }

    public void addUserAdvice(String string)
    {
        logger.debug("Called addUserAdvice({})", string);
    }

    public void buildCoordinateSystems(NetcdfDataset netcdfDataset)
    {
        logger.debug("Called buildCoordinateSystems()");
        super.buildCoordinateSystems(netcdfDataset);
    }

    public void augmentDataset(NetcdfDataset netcdfDataset, CancelTask cancelTask) throws IOException
    {
        logger.debug("Called augmentDataset()");
    }
    
}