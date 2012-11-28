package ch.maybites.alpha.capture;

import processing.core.PImage;

public class DetectionDiagram {
	
	int[][] m_diagram;
	int m_diagSize;
	int[] m_value;
	
	public DetectionDiagram(int[][] p_diagram){
		m_diagram = p_diagram;
		m_diagSize = (int)Math.sqrt((double) m_diagram.length);
		m_value = new int[m_diagram.length];
	}
	
	public void analyze(PImage p_img, int p_neutral, int p_thresh){
		int f_dgPaarcellWidth = (p_img.width / m_diagSize);
		int f_dgPaarcellHeight = (p_img.height / m_diagSize);
		int temp;
		for(int y = 0; y < m_diagSize; y++){
			for(int x = 0; x < m_diagSize; x++){
				m_value[x + y * m_diagSize] = 0;
				for(int dy = 0; dy < f_dgPaarcellHeight; dy++){
					for(int dx = 0; dx < f_dgPaarcellWidth; dx++){
						temp = ((p_img.pixels[x * f_dgPaarcellWidth + dx + (y * f_dgPaarcellHeight + dy)*p_img.width]) & 0xff);
						if(temp > p_thresh){
							m_value[x + y * m_diagSize] += temp;
						}
					}
				}
				//m_value[x + y * m_diagSize] /= f_dgPaarcellWidth * f_dgPaarcellHeight; 
				m_value[x + y * m_diagSize] *= m_diagram[x + y * m_diagSize][1]; 
			}
		}
	}
	
	public int getValue(int p_number){
		for(int t = 0; t < m_value.length; t++){
			if(m_diagram[t][0] == p_number){
				return m_value[t];
			}
		}
		return 0;
	}
}
