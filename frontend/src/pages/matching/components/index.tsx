import React, { useEffect, useRef, useState } from 'react';
import * as d3 from 'd3';

interface MatchingQueueBubbleChartProps {
  data: {
    hashtags: string[];
    counts: { [key: string]: number };
  };
}

const MatchingQueueBubbleChart: React.FC<MatchingQueueBubbleChartProps> = ({ data }) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const [bubbleData, setBubbleData] = useState<{ label: string; value: number }[]>([]);

  useEffect(() => {
    if (!svgRef.current) return;

    const svg = d3.select(svgRef.current);
    const width = +svg.attr('width');
    const height = +svg.attr('height');

    // 데이터를 D3용 버블 차트 데이터 형식으로 변환
    const newBubbleData = data.hashtags.map((tagName) => ({
      label: tagName,
      value: data.counts[tagName] || 0,
    }));

    setBubbleData(newBubbleData);

    
    
    // D3 코드 시작
    const simulation = d3.forceSimulation(newBubbleData)
      .force('charge', d3.forceManyBody().strength(5))
      .force('x', d3.forceX(width / 2))
      .force('y', d3.forceY(height / 2))
      .force('collision', d3.forceCollide().radius(d => Math.sqrt(d.value) * 10));

    const circles = svg.selectAll('circle').data(newBubbleData);

    circles.enter().append('circle')
      .attr('fill', 'steelblue')
      .merge(circles)
      .attr('cx', d => d.x)
      .attr('cy', d => d.y)
      .attr('r', d => Math.sqrt(d.value) * 10);

    console.log(circles)
    circles.exit().remove();

    simulation.on('tick', () => {
      circles.attr('cx', d => d.x).attr('cy', d => d.y);
    });

    // D3 코드 종료
  }, [data]);

  return (
    <svg ref={svgRef} width={500} height={300}></svg>
  );
};

export default MatchingQueueBubbleChart;
