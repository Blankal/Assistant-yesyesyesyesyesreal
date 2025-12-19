
# PA
  
## Usage Instructions:
### 1. This project requires that you install **Conda**, the python environment manager -> [Anaconda](https://www.anaconda.com/download/success)
We used **MiniConda** which is a lighter version of the bulkier full install but I'm not really sure of the difference so probably just use whatever runs.  
For now you must activate the conda environment before running the program with the commands however this will change in future updates.  
To activate the environment, type `cd src/main/java/com/localAgents/OmniParser/` and then `conda activate "omni"` into your terminal

#### 1.5 If you don't hate yourself and would like to use your NVIDIA GPU or APPLE SILICON MPS you must run these commands to install a package variation to enable usage of your GPU:
```
conda activate omni
pip uninstall torch torchvision torchaudio ultralytics -y
pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu121
pip install ultralytics
python -c "import torch; print(torch.cuda.is_available())" 
```
If the last command (`python -c "import torch; print(torch.cuda.is_available())"`) returns a positive result, you may use your GPU for the program, otherwise you should delete those packages with  
```
pip uninstall torch torchvision torchaudio
pip uninstall ultralytics
```
> Keep in mind that performance depends on the GPU used. Ex: This was tested on 2 computers using llama3.2, one with an rtx 4060 (8gb VRAM) and one with an rtx 3060 (12gb VRAM). 4060 takes on average 4.5 seconds while the 3060 requires about 9 seconds on average.  
> This is a problem with OmniParser(image processing program) and is a fundamental issue that cannot be optimized out at the moment.  
> You can view the total time it takes to process an OmniParser request under the terminal that runs the OmniParser server. 
  
### 2. Download Python File Requirements
Once you have downloaded Conda, navigate to the OmniParser folder if you are not still there. Before you start this **MAKE ABSOLUTELY SURE** you are using the python version within the Conda environment. If you install these things wrong your computer won't explode and it is reversible easily however anything running Python has a chance to break and this program definitely will not work. If you are running from VSCode, the python version is in the bottom right. **AGAIN REMEMBER THE CORRECT VERSION** which is called **conda 12.0.0**. Once you are absolutely sure of the version you are using, you can run `pip install -r "requirements.txt"` to install all the necessary packages.

### 2.5 It is also recommended that you obtain an API key from one of the following sites:
#### Google ([Gemini](https://ai.google.dev)), OpenAI ([GPT](https://openai.com))  
> Note: Google API is not currently supported, it will be supported again in a future update, for now use OpenAI keys(or add support yourself)  
> If you are choosing a model, this program is very token heavy, it will stop become overwhelmed and the machine spirit will give up if it cannot handle enough.  
> It is recommended to choose something in the range of 60 - 100k range for general usage.

 Although you do not explicitly need an API key, it may be used to make smarter/faster responses as the model does not have to run on your computer and is trained on more data than a local model. **If you are trying to optimize performance, changing to a non-local model will not really make the program much faster**. The main importance of a key is to have a smarter model working on the task to improve coherence and answering capability, local models also genrally cannot access the internet.  
 To use a personal key, you must set its' environment variable which is usually defined on their websites. For example Gemini/Google models use 'GOOGLE_API_KEY' and OpenAI use 'OPENAI_API_KEY' as their monikers.

 > This is a program intended for PC use mainly as laptops are not extremely powerful however can be run with a bit mmore than slight performance issues using an API key if you want to use a smarter model

### 3. Starting the Server
Once the requirements are installed, be sure to run the file "omniparserserver.py" under the path "src/main/java/com/localAgents/OmniParser/omnitool/omniparserserver/omniparserserver.py" in order to boot the server. It will take around 10 seconds on most computers (I think?) and you will have to do this every time you run this program although the server doesn't shut off unless you close the terminal window.

### 4. Setting the Config
The only things under config you should really need to change are 
1. Model brand (like "Google" or "OpenAI"), 2. Model type(like "Llama3.1, Llama3.2, Phi3:medium-128k), and the **USER** prompt. The System prompt is dangerous to mess with and could lead to the program not working unless you know what you're doing so it's recommended not to.

### 5. Last Step
idk I just wanted 5 steps pray that it works or something.

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

#### 3. "OmniParser/OmniParserServer keeps having issues importing things(PaddleOCR, Torch, etc.)!!!
If OmniParser or its' server has issues importing files, this is a very clear sign that you A. Did not download the requirements.txt file correctly or B. 

#### 4. JAVA.NET.CONNECT_EXCEPTION or "OmniParser loads forever or won't take any requests!!!"
If the above fixes do not work, you can attempt to change the address in config and the "omniparserserver" file. Most often this happens because A. You forgot to run Ollama/AI cannot use the key you gave it or B. The port/IP address is already in use on your computer.

#### 5. "The terminal keeps saying it can't find Ollama when I use it!!!"
If Ollama (program, not model) is not running, it will not be executed even when the path is correct. To start Ollama, try clicking on the app or running a model with Ollama run llamaModelName to warm it up, this will also install the model you type in if it exists and you do not have it. If you do not have Ollama installed already, here is the [install link](https://ollama.com/download)

#### 6. "Java keeps saying it can't import packages!!!"
If Java is saying it cannot find packages, you may have to install and set up [Apache Maven]() and run `mvn clean install`.

#### 7. "None of these fixed my issue!!!"
Learn to code or something man idk

---

## Contact Info
You can contact us at CreativeEmailName67@gmail.com for feature suggestions/support  
*We don't read emails often though I will try my best to check - Caleb*

---
## Acknowledgements
This project makes use of the following tools, libraries and models along with their dependencies:

- **Apache Maven** - Build automation and dependency manager
 https://maven.apache.org

- **Ollama** - Local large language model for free usage
 https://ollama.com

- **OmniParser** - Free agentic software made by windows for structured data extraction of screenshots (requirements modified for paddleocr==2.6.2)
 https://github.com/microsoft/omniparser