<template>
  <div class="calculator-wrapper">
    <h1 class="title">Calculator Cucumber</h1>
    
    <div id="calculator">
      <input id="display" :value="display" readonly placeholder="0">

      <div id="keys">
        <button @click="clearDisplay" class="operator-btn btn-c">C</button>
        <button @click="deleteLast" class="operator-btn del-btn">DEL</button>
        <button @click="appendToDisplay('/')" class="operator-btn">/</button>

        <button @click="appendToDisplay('7')">7</button>
        <button @click="appendToDisplay('8')">8</button>
        <button @click="appendToDisplay('9')">9</button>
        <button @click="appendToDisplay('*')" class="operator-btn">x</button>

        <button @click="appendToDisplay('4')">4</button>
        <button @click="appendToDisplay('5')">5</button>
        <button @click="appendToDisplay('6')">6</button>
        <button @click="appendToDisplay('-')" class="operator-btn">-</button>

        <button @click="appendToDisplay('1')">1</button>
        <button @click="appendToDisplay('2')">2</button>
        <button @click="appendToDisplay('3')">3</button>
        <button @click="appendToDisplay('+')" class="operator-btn">+</button>

        <button @click="appendToDisplay('0')">0</button>
        <button @click="appendToDisplay('.')">.</button>
        <button @click="showHelp" class="help-btn">?</button>
        <button @click="calculate" class="operator-btn equal-btn">=</button>
      </div>
    </div>

    <div v-if="isHelpVisible" class="modal-overlay" @click="closeHelp">
      <div class="modal-content" @click.stop>
        <h2>Calculator help</h2>
          <ul>
            <li>Enter a math expression (e.g., 2 + 3) and press '='.</li>
            <li>Calculations are processed by a Java server</li>
          </ul>
        <button class="close-modal-btn" @click="closeHelp">OK</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const display = ref('');
const isHelpVisible = ref(false);

const appendToDisplay = (value) => {
  display.value += value;
};

const clearDisplay = () => {
  display.value = '';
};

const deleteLast = () => {
  display.value = display.value.slice(0, -1);
};

const calculate = async () => {
  if (!display.value) return;
  
  try {
    const response = await axios.post('http://localhost:8080/api/evaluate', {
      expression: display.value
    });
    
    display.value = response.data.result;
  } catch (error) {
    alert("Calculation error ! Please try again with another expression.");
    console.error(error);
  }
};

const showHelp = () => {
  isHelpVisible.value = true;
};

const closeHelp = () => {
  isHelpVisible.value = false;
};
</script>

<style scoped>
.calculator-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.title {
  color: #4ade80;
  font-size: clamp(1.8rem, 5vw, 2.5rem); 
  margin-bottom: 3vh;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

#calculator {
  font-family: Arial, sans-serif;
  background-color: #242424;
  border-radius: 20px;
  padding: 5%;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.5);
  width: 90%; 
  max-width: 360px;
}

#display {
  width: 100%;
  padding: 5%;
  font-size: clamp(2.5rem, 10vw, 3.5rem);
  text-align: right;
  border: none;
  background-color: #1a1a1a;
  color: white;
  border-radius: 10px;
  margin-bottom: 6%;
  box-sizing: border-box;
  outline: none;
  user-select: none;
  pointer-events: none;
}

#keys {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 4%; 
}

button {
  width: 100%;
  aspect-ratio: 1 / 1;
  border-radius: 50%;
  border: none;
  background-color: #3f3f46;
  color: white;
  font-size: clamp(2.2rem, 5vw, 1.8rem);
  font-weight: bold;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

button:hover { background-color: #52525b; }
button:active { background-color: #71717a; }

.operator-btn { background-color: #f59e0b; }
.operator-btn:hover { background-color: #fbbf24; }
.operator-btn:active { background-color: #fcd34d; }

.btn-c {
  grid-column: span 2; 
  aspect-ratio: auto; 
  border-radius: 9999px;
  height: 100%;
}

.del-btn { font-size: clamp(0.9rem, 4vw, 1.3rem); }

.help-btn { background-color: #3b82f6; }
.help-btn:hover { background-color: #60a5fa; }

.modal-overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100vw; height: 100vh;
  background-color: rgba(0, 0, 0, 0.8);
  backdrop-filter: blur(5px);
  display: flex; justify-content: center; align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: #242424;
  padding: 3rem;
  border-radius: 25px;
  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.8);
  text-align: left;

  max-width: 450px; 
  width: 90%;
  min-height: 100px; 
  
  color: #fff;
}

.modal-content h2 {
  color: #4ade80;
  text-align: center;
  font-size: 2rem;
  margin-top: 0;
  margin-bottom: 1.5rem;
}

.help-body ul {
  font-size: 1.2rem;
  line-height: 1.6;
}

.help-body li {
  margin-bottom: 1rem;
  list-style-type: none;
}

.close-modal-btn {
  display: block;
  margin: 2rem auto 0;
  width: auto;
  aspect-ratio: auto;
  border-radius: 12px;
  background-color: #4ade80;
  color: #1a1a1a;
  padding: 15px 40px;
  font-size: 1.2rem;
}

.close-modal-btn:hover { background-color: #22c55e; }
</style>