
# PA
## Usage Instructions:
### 1. This project requires you to install Conda, the python environment manager -> [Anaconda](https://www.anaconda.com/download/success)
For now you must activate the conda environment before running the program with the commands however this will change in future updates.
To activate the environment, type `cd \path\to\localAgents\OmniParser\` and then `conda activate "omni"` into your terminal

### 2. It is also recommended that you obtain an API key from one of the following sites:
#### Google ([Gemini](https://ai.google.dev)), OpenAI ([GPT](https://openai.com))
 Although you do not explicitly need an API key, it may be used to make smarter/faster responses as the model does not have to run on your computer and is trained on more data.
 To use a personal key, you must set its' environment variable which is usually defined on their websites. For example Gemini/Google models use 'GOOGLE_API_KEY' and OpenAI use 'OPENAI_API_KEY' as their monikers.

 > Note: If you are not on a Mac computer or do not own an NVIDIA GPU, the image processing will take significantly longer(10-12x)
 > This is a program intended for PC use mainly as laptops are not extremely powerful however can be run with slight performance issues using an API key

### 3. After setting your environment, be sure to edit config.java for use-case.
The only things under config you should really need to change are 1. Model brand, 2. Model type, the prompt is more like pre instructions to orient the model than a user input thing.

---

## Common Bug Fixes:
#### 1. "When I install the requirements it warns me about installing to the whole computer or runs my python instead of Conda's python!!!"
If your version of python is being run through conda, you may have forgotten to initialize conda in your terminal. Depending on which terminal you use this program from it will be different. If you run this program from VSCode for example you should use the **bash** version of the init command. Below are the possible commands:
```
conda init bash
conda init powershell
conda init cmd.exe
```
#### 2. "OmniParser keeps saying it doesn't work or failed to initialize in the terminal!!!"
If OmniParser will not work after the conda environment is activated you might need to run 
`pip install -r requirements.txt` under the OmniParser folder in order to install the packages required by Omni
>Note: Do not install these outside of the conda environment as they could conflict with other programs or scripts and cause problems

#### 3. "OmniParser loads forever or won't take any requests!!!"
If the above fixes do not work, you can attempt to change the address in config and the "omniparserserver" file ADD MORE INFO LATER

#### 4. "The terminal keeps saying it can't find Ollama when I use it!!!"
If Ollama (program, not model) is not running, it will not be executed even when the path is correct. To start Ollama, try clicking on the app or running a model with Ollama run llamaModelName to warm it up, this will also install the model you type in if it exists and you do not have it. If you do not have Ollama installed already, here is the [install link](https://ollama.com/download)

#### 5. "None of these fixed my issue!!!"
Be a programmer bruh

---

## Acknowledgements
This project makes use of the following tools, libraries and models along with their dependencies:

- **Apache Maven** - Build automation and dependency manager
 https://maven.apache.org

- **Ollama** - Local large language model for free usage
 https://ollama.com

- **OmniParser** - Free agentic software made by windows for structured data extraction of screenshots (requirements modified for paddleocr==2.6.2)
 https://github.com/microsoft/omniparser