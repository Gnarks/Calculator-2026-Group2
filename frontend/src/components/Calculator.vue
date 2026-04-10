<template>
  <div class="calculator-wrapper">    
    <div id="calculator">
      <div id="display">{{ display || '0' }}</div>

      <div class="precision-wrapper">
        <span>Precision: <strong>{{ precision }}</strong></span>
        <div class="prec-controls">
          <button @click="decreasePrecision" class="prec-btn">-</button>
          <button @click="increasePrecision" class="prec-btn">+</button>
        </div>
      </div>

      <div class="scientific-keys">
        <button @click="appendToDisplay('sin(')" class="func-btn">sin</button>
        <button @click="appendToDisplay('cos(')" class="func-btn">cos</button>
        <button @click="appendToDisplay('tan(')" class="func-btn">tan</button>
        <button @click="appendToDisplay('sqrt(')" class="func-btn">sqrt</button>
        <button @click="appendToDisplay('**')" class="func-btn">^</button>

        <button @click="appendToDisplay('asin(')" class="func-btn">asin</button>
        <button @click="appendToDisplay('acos(')" class="func-btn">acos</button>
        <button @click="appendToDisplay('atan(')" class="func-btn">atan</button>
        <button @click="appendToDisplay('log(')" class="func-btn">log</button>
        <button @click="appendToDisplay('ln(')" class="func-btn">ln</button>

        <button @click="appendToDisplay('sinh(')" class="func-btn">sinh</button>
        <button @click="appendToDisplay('cosh(')" class="func-btn">cosh</button>
        <button @click="appendToDisplay('tanh(')" class="func-btn">tanh</button>
        <button @click="appendToDisplay('(')" class="func-btn">(</button>
        <button @click="appendToDisplay(')')" class="func-btn">)</button>

        <button @click="appendToDisplay('pi')" class="const-btn">π</button>
        <button @click="appendToDisplay('e')" class="const-btn">e</button>
        <button @click="appendToDisplay('E')" class="func-btn">E</button>
        <button @click="appendToDisplay('i')" class="const-btn">i</button>
        <button @click="appendToDisplay(',')" class="func-btn">,</button>
        
        <button @click="showRandomModal" class="func-btn rnd-btn">RNG</button>
      </div>

      <div class="standard-keys">
        <button @click="clearDisplay" class="calc-op-btn btn-c orange-btn">C</button>
        <button @click="deleteLast" class="calc-op-btn del-btn orange-btn">DEL</button>
        <button @click="showHelp" class="orange-btn">?</button>
        <button @click="appendToDisplay('//')" class="calc-op-btn orange-btn">÷</button>

        <button @click="appendToDisplay('7')">7</button>
        <button @click="appendToDisplay('8')">8</button>
        <button @click="appendToDisplay('9')">9</button>
        <button @click="appendToDisplay('*')" class="calc-op-btn orange-btn">x</button>

        <button @click="appendToDisplay('4')">4</button>
        <button @click="appendToDisplay('5')">5</button>
        <button @click="appendToDisplay('6')">6</button>
        <button @click="appendToDisplay('-')" class="calc-op-btn orange-btn">-</button>

        <button @click="appendToDisplay('1')">1</button>
        <button @click="appendToDisplay('2')">2</button>
        <button @click="appendToDisplay('3')">3</button>
        <button @click="appendToDisplay('+')" class="calc-op-btn orange-btn">+</button>

        <button @click="appendToDisplay('/')">/</button>
        <button @click="appendToDisplay('0')" class="btn-zero">0</button>
        <button @click="appendToDisplay('.')">.</button>
        <button @click="calculate" class="calc-op-btn equal-btn">=</button>
      </div>
    </div>

    <div v-if="isHelpVisible" class="modal-overlay" @click="closeHelp">
      <div class="modal-content" @click.stop>
        <h2>Calculator Help</h2>
        <div class="help-body">
          <ul>
            <li><strong>Operations:</strong> Enter your math expression (e.g., <code class="math-preview">cos(pi) + 3 * 5</code>) and press <span class="key-hint">=</span>.</li>
            <li><strong>Logarithm:</strong> <code class="math-preview">log</code> takes 2 parameters (e.g., <code class="math-preview">log(8, 2)</code>).</li>
            <li><strong>Precision:</strong> Adjust the number of decimal places calculated by the server.</li>
            <li><strong>Editing:</strong>
              <ul>
                <li>Press <span class="key-hint">C</span> to clear the entire screen.</li>
                <li>Press <span class="key-hint">DEL</span> to remove the last character.</li>
              </ul>
            </li>
            <li> Calculations are processed by a <strong>Java</strong> server.</li>
          </ul>
        </div>
        <button class="close-modal-btn" @click="closeHelp">OK</button>
      </div>
    </div>

    <div v-if="isRandomVisible" class="modal-overlay" @click="closeRandomModal">
      <div class="modal-content" @click.stop>
        <h2>Random number generator</h2>
        <div class="form-group">
          <label>Type:</label>
          <select v-model="randType">
            <option value="INTEGER">Integer</option>
            <option value="REAL">Real</option>
            <option value="RATIONNAL">Rational</option>
            <option value="COMPLEX">Complex</option>
          </select>
        </div>
        <div class="form-group">
          <label>Max (> 0):</label>
          <input type="number" v-model="randMax" min="1" />
        </div>
        <div class="form-group">
          <label>Seed</label>
          <input type="number" v-model="randSeed" placeholder="Optional field" />
        </div>
        <div class="modal-actions">
          <button class="close-modal-btn cancel-btn" @click="closeRandomModal">Cancel</button>
          <button class="close-modal-btn" @click="generateRandom">Generate</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const display = ref('');
const isHelpVisible = ref(false);
const isResultState = ref(false);

const isRandomVisible = ref(false);
const randType = ref('INTEGER');
const randMax = ref(100);
const randSeed = ref('');

// default = 64
const precision = ref(64);

const increasePrecision = () => {
  precision.value++;
};

const decreasePrecision = () => {
  if (precision.value > 0) {
    precision.value--;
  }
};

const appendToDisplay = (value) => {
  if (isResultState.value) return;
  if (display.value === 'Error') display.value = '';
  display.value += value;
};

const clearDisplay = () => {
  display.value = '';
  isResultState.value = false;
};

const deleteLast = () => {
  if (display.value == "Error" || isResultState.value){
    clearDisplay();
    return;
  }
  display.value = display.value.slice(0, -1);
};

const calculate = async () => {
  if (!display.value || display.value === 'Error') return;

  // if in production : request on /api
  if (import.meta.env.PROD){
	  
    try {
      const response = await axios.post('/api/evaluate',
        {
          expression: display.value,
          precision: precision.value
        },
        {
          headers: {
            scheme: 'https'
    }}
      );

      if (response.data.success === 1) {
        display.value = response.data.result.toString();
        isResultState.value = true;
      } else {
        display.value = 'Error';
        isResultState.value = false;
        console.warn("error :", response.data.result);
      }

    } catch (error) {
      alert("Impossible to reach server. Please try again later");
      display.value = 'Error';
      console.error(error);
    }
  }
  // otherwise (in dev) request on localhost without https
  else {
    try {
      const response = await axios.post('http://localhost:1523/api/evaluate',
        {
          expression: display.value,
          precision: precision.value
        },
      );

      if (response.data.success === 1) {
        display.value = response.data.result.toString();
        isResultState.value = true;
      } else {
        display.value = 'Error';
        isResultState.value = false;
        console.warn("error :", response.data.result);
      }


    } catch (error) {
      alert("Impossible to reach server. Please try again later");
      display.value = 'Error';
      console.error(error);
    }
  }
};

const showHelp = () => {
  isHelpVisible.value = true;
};

const closeHelp = () => {
  isHelpVisible.value = false;
}

const showRandomModal = () => { 
  isRandomVisible.value = true; 
};
const closeRandomModal = () => { 
  isRandomVisible.value = false; 
}

const generateRandom = async () => {
  if (randMax.value <= 0) {
    alert("Max must be strictly greater than 0");
    return;
  }

  const params = {
    type: randType.value,
    max: randMax.value
  };
  
  if (randSeed.value !== '' && randSeed.value !== null) {
    params.seed = randSeed.value;
  }

  // if in production : request on /api
  if (import.meta.env.PROD) {
    try {
      const response = await axios.get('/api/random', {
        params: params,
        headers: {
          scheme: 'https'
        }
      });

      if (response.data.success === 1 || response.data.result !== undefined) {
        if (isResultState.value || display.value === 'Error') {
          clearDisplay();
        }
        appendToDisplay(response.data.result.toString());
        closeRandomModal();
      } else {
        console.warn("error :", response.data);
        alert("Error generating random number");
      }

    } catch (error) {
      alert("Impossible to reach server. Please try again later");
      console.error(error);
    }
  } 
  // otherwise (in dev) request on localhost without https
  else {
    try {
      const response = await axios.get('http://localhost:1523/api/random', {
        params: params
      });

      if (response.data.success === 1 || response.data.result !== undefined) {
        if (isResultState.value || display.value === 'Error') {
          clearDisplay();
        }
        appendToDisplay(response.data.result.toString());
        closeRandomModal();
      } else {
        console.warn("error :", response.data);
        alert("Error generating random number");
      }

    } catch (error) {
      alert("Impossible to reach server. Please try again later");
      console.error(error);
    }
  }
};

</script>

<style scoped>
.calculator-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  padding: 20px;
}

.title {
  color: #4ade80;
  font-size: clamp(1.8rem, 5vw, 2.5rem);
  margin-bottom: 20px;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

#calculator {
  font-family: Arial, sans-serif;
  background-color: #242424;
  border-radius: 20px;
  padding: 25px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.5);
  width: 100%;
  max-width: 380px;
  box-sizing: border-box;
}

#display {
  width: 100%;
  padding: 20px;
  font-size: clamp(2rem, 8vw, 3rem);
  text-align: right;
  border: none;
  background-color: #1a1a1a;
  color: white;
  border-radius: 12px;
  margin-bottom: 15px;
  box-sizing: border-box;
  outline: none;
  user-select: none;
  overflow-x: auto;
  white-space: nowrap;
  scrollbar-color: #4ade80 #1a1a1a;
}

.precision-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #1a1a1a;
  padding: 8px 15px;
  border-radius: 8px;
  margin-bottom: 20px;
  color: #a1a1aa;
  font-size: 0.9rem;
}

.precision-wrapper strong {
  color: #4ade80;
  font-size: 1.1rem;
  margin-left: 5px;
}

.prec-controls {
  display: flex;
  gap: 10px;
}

#calculator .prec-btn {
  width: 32px;
  height: 32px;
  border-radius: 6px;
  background-color: #3f3f46;
  font-size: 1.2rem;
  color: white;
}

#calculator .prec-btn:hover {
  background-color: #52525b;
}

#calculator .prec-btn:active {
  background-color: #4ade80;
  color: #1a1a1a;
}

.scientific-keys {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 8px;
  margin-bottom: 20px;
}

.standard-keys {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
}

#calculator button {
  width: 100%;
  min-height: 0;
  min-width: 0;
  border: none;
  background-color: #3f3f46;
  color: white;
  font-weight: bold;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

#calculator button:hover { background-color: #52525b; }
#calculator button:active { background-color: #71717a; }

#calculator .scientific-keys button {
  height: 40px;
  border-radius: 6px;
  font-size: clamp(0.8rem, 3vw, 1rem);
  background-color: #52525b;
}

#calculator .scientific-keys button:hover { background-color: #71717a; }
#calculator .scientific-keys button:active { background-color: #a1a1aa; }

.rnd-btn { color: #fbbf24 !important; }

.standard-keys button {
  aspect-ratio: 1 / 1;
  border-radius: 50%;
  font-size: 1.4rem;
}

#calculator .orange-btn {
  background-color: #f59e0b;
  color: white;
}

#calculator .orange-btn:hover { background-color: #fbbf24; }
#calculator .orange-btn:active { background-color: #fcd34d; }



.btn-zero {
  aspect-ratio: unset !important;
  height: 100%;
  border-radius: 40px !important;
}

.del-btn { font-size: 1.1rem; }

.modal-overlay {
  position: fixed; top: 0; left: 0; width: 100vw; height: 100vh;
  background-color: rgba(0, 0, 0, 0.8); backdrop-filter: blur(5px);
  display: flex; justify-content: center; align-items: center; z-index: 1000;
}
.modal-content {
  background-color: #242424; padding: 3rem; border-radius: 25px;
  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.8); text-align: left;
  max-width: 450px; width: 90%; min-height: 100px; color: #fff;
  box-sizing: border-box;
}
.modal-content h2 { color: #4ade80; text-align: center; font-size: 2rem; margin-top: 0; margin-bottom: 1.5rem; }

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.form-group label {
  color: #a1a1aa;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input, .form-group select {
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #3f3f46;
  background-color: #1a1a1a;
  color: white;
  font-size: 1rem;
  outline: none;
}

.form-group input:focus, .form-group select:focus {
  border-color: #4ade80;
}

.modal-actions {
  display: flex;
  justify-content: space-between;
  gap: 15px;
  margin-top: 2rem;
}

.help-body ul { font-size: 1.1rem; line-height: 1.6; list-style-type: none; padding-left: 0; }
.help-body li { margin-bottom: 1rem; }
.help-body ul ul { margin-top: 0.5rem; padding-left: 1.5rem; }
.help-body ul ul li { margin-bottom: 0.5rem; font-size: 0.95rem; }
.close-modal-btn {
  display: block; margin: 2rem auto 0; width: auto; aspect-ratio: auto;
  border-radius: 12px; background-color: #4ade80; color: #1a1a1a;
  padding: 15px 40px; font-size: 1.2rem;
}
.close-modal-btn:hover { background-color: #22c55e; }

.cancel-btn {
  background-color: #f59e0b;
}

.cancel-btn:hover { background-color: #fbbf24; }

.key-hint { background-color: #3f3f46; padding: 2px 8px; border-radius: 4px; font-family: monospace; border-bottom: 2px solid #1a1a1a; color: #4ade80; font-weight: bold; }
.math-preview { color: #fbbf24; font-style: italic; }
</style>
