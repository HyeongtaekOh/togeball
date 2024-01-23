import React from 'react';
import ReactWordcloud from 'react-wordcloud';
import 'tippy.js/dist/tippy.css';
import 'tippy.js/animations/scale.css';

const SimpleWordcloud = () => {
    const words = [
      {
        text: 'told',
        value: 1264,
      },
      {
        text: 'mistake',
        value: 64,
      },
      {
        text: 'thought',
        value: 64,
      },
      {
        text: 'bad',
        value: 64,
      },
    ]
    
  return <ReactWordcloud words={words}  />
}

export default SimpleWordcloud;