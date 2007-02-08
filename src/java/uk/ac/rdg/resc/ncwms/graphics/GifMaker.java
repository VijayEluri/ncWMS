/*
 * Copyright (c) 2007 The University of Reading
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

package uk.ac.rdg.resc.ncwms.graphics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import net.jmge.gif.Gif89Encoder;
import org.apache.log4j.Logger;

/**
 * Creates (possibly animated) GIFs.
 *
 * @author Jon Blower
 * $Revision$
 * $Date$
 * $Log$
 */
public class GifMaker extends PicMaker
{
    private static final Logger logger = Logger.getLogger(GifMaker.class);
    
    private Gif89Encoder encoder;
    private ArrayList<float[]> frameData;
    private ArrayList<String> frameLabels;
    
    /** Creates a new instance of GifMaker */
    public GifMaker()
    {
        this.encoder = new Gif89Encoder();
        this.frameData = null;
        this.frameLabels = null;
        logger.debug("Created GifMaker");
    }

    /**
     * Adds a frame to this animation.  If the colour scale hasn't yet been set
     * we cache the data and render the frames later
     */
    public void addFrame(float[] data, String label) throws IOException
    {
        logger.debug("Adding frame with label {}...", label);
        if (this.isAutoScale())
        {
            logger.debug("  ... auto-scaling, so caching frame");
            if (this.frameData == null)
            {
                this.frameData = new ArrayList<float[]>();
                this.frameLabels = new ArrayList<String>();
            }
            this.frameData.add(data);
            this.frameLabels.add(label);
        }
        else
        {
            logger.debug("  ... colour scale already set, so creating image of frame");
            this.encoder.addFrame(this.createFrame(data, label));
        }
    }

    public void writeImage(OutputStream out) throws IOException
    {
        logger.debug("writing GIF...");
        if (this.frameData != null)
        {
            logger.debug("  ... we must set the colour scale");
            // We have a cache of image data, which we need to turn into images
            // First we set the colour scale correctly
            for (float[] data : this.frameData)
            {
                this.adjustColourScaleForFrame(data);
            }
            logger.debug("  ... colour scale set, rendering stored frames...");
            // Now we render the frames
            for (int i = 0; i < this.frameData.size(); i++)
            {
                logger.debug("    ... rendering frame {}", i);
                this.encoder.addFrame(this.createFrame(this.frameData.get(i),
                    this.frameLabels.get(i)));
            }
        }
        if (this.encoder.getFrameCount() > 1)
        {
            logger.debug("Animated GIF ({} frames), setting loop count and delay",
                this.encoder.getFrameCount());
            // this is an animated GIF.  Set to loop infinitely.
            this.encoder.setLoopCount(-1);
            this.encoder.setUniformDelay(15); // delay between frames in centiseconds
        }
        logger.debug("Writing GIF to output stream ...");
        this.encoder.encode(out);
        logger.debug("  ... written.");
    }
    
}
