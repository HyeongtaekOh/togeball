import React, { useEffect, useRef, useState } from 'react'
import * as d3 from 'd3'

const MatchingQueue = (props) => {

  const { data } = props

  const svgRef = useRef(null)
  const [ bubbleData, setBubbleData ] = useState([])

  useEffect(() => {
    if (!svgRef.current) return
    const parentHeight = svgRef.current.parentElement.clientHeight

    const svg = d3.select(svgRef.current)
    const width = +svg.attr('width')
    const height = parentHeight

    const newBubbleData = data.hashtags?.map(( tagName ) => ({
      label: tagName,
      value: data?.counts[tagName],
      x: Math.random() * width, // 랜덤한 X 좌표
      y: Math.random() * height, // 랜덤한 Y 좌표
    }));

    setBubbleData(newBubbleData)

    const simulation = d3.forceSimulation(newBubbleData)
      .force('charge', d3.forceManyBody().strength(5))
      .force('x', d3.forceX(width / 2).strength(0.1)) // 중력을 적용하여 중앙으로 모이도록 설정
      .force('y', d3.forceY(height / 2).strength(0.1)) // 중력을 적용하여 중앙으로 모이도록 설정
      .force('collision', d3.forceCollide().radius(d => Math.sqrt(d.value) * 60))
      .on('tick', () => {
        circles.attr('cx', d => d.x).attr('cy', d => d.y)
        texts.attr('x', d => d.x).attr('y', d => d.y)
      })

    const filter = svg.append('defs').append('filter').attr('id', 'blur-filter').append('feGaussianBlur').attr('stdDeviation', 5)

    const circles = svg.selectAll('circle').data(newBubbleData)

    circles.enter().append('circle')
      .attr('fill', '#FCF1FC')
      .attr('stroke', '#FCE5FC')
      .attr('stroke-width', 10)
      .style('filter', 'url(#blur-filter)')
      .merge(circles)
      .attr('cx', d => d.x)
      .attr('cy', d => d.y)
      .attr('r', d => Math.sqrt(d.value) * 60)
      .call(blinkAnimation) // 애니메이션 함수 호출

    // 텍스트 추가
    const texts = svg.selectAll('text').data(newBubbleData);

    texts.enter().append('text')
      .attr('text-anchor', 'middle')
      .attr('dy', '0.35em')
      .merge(texts)
      .attr('x', d => d.x)
      .attr('y', d => d.y)
      .text(d => d.label)

    circles.exit().remove()
    texts.exit().remove()

    simulation.on('tick', () => {
      circles.attr('cx', d => d.x).attr('cy', d => d.y)
      texts.attr('x', d => d.x).attr('y', d => d.y)
    });
  }, [ data ])

  // 애니메이션을 위한 함수
  const blinkAnimation = (selection) => {
    selection
      .transition()
      .duration(500)
      .attr('fill-opacity', 0.5)
      .transition()
      .duration(500)
      .attr('fill-opacity', 1)
      .on('end', () => blinkAnimation(selection)); // 재귀적으로 호출하여 무한 반복
  }

  return (
    <svg ref={svgRef} width={1000} height='100%' style={{ backgroundColor: 'white' }}>
      <style>
        {`
          text {
            font-size: 20px;
            font-family: 'Ibm-Medium';
          }
        `}
      </style>
    </svg>
  )
}

export default MatchingQueue
