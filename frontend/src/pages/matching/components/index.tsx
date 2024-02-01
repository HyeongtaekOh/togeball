import React, { useEffect, useRef } from 'react';
import * as d3 from 'd3';

interface BubbleChartProps {
  data: { name: string; value: number; radius: number }[];
}

const BubbleChart: React.FC<BubbleChartProps> = ({ data }) => {
  const svgRef = useRef<SVGSVGElement | null>(null);

  useEffect(() => {
    if (!svgRef.current) return;

    const svg = d3.select(svgRef.current);

    // Create simulation with collision detection
    const simulation = d3.forceSimulation(data)
      .force('charge', d3.forceManyBody().strength(5))
      .force('x', d3.forceX().x(d => d.x))
      .force('y', d3.forceY().y(d => d.y))
      .force('collision', d3.forceCollide().radius(d => d.radius + 2));

    // Update nodes on each tick
    simulation.on('tick', () => {
      svg.selectAll('.bubble')
        .attr('cx', d => d.x)
        .attr('cy', d => d.y);
    });

    // Append circles to SVG
    svg.selectAll('.bubble')
      .data(data)
      .enter().append('circle')
        .attr('class', 'bubble')
        .attr('r', d => d.radius)
        .style('fill', 'steelblue');

  }, [data]);

  return (
    <svg ref={svgRef}></svg>
  );
};

export default BubbleChart;