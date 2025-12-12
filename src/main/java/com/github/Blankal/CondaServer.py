from fastapi import FastAPI
from com.localAgents.OmniParser.util.omniparser import parse
import base64
import io
from PIL import Image

app = FastAPI()
parser = OmniParser()

"""
Parses and analyzes a b64 encoded image to find its' elements and coordinates.
:param b64Image: base64 image string
:return: Json string containing a list of found elements in the image + coordinates
"""


@app.post("/parse/")
async def parse_image(b64Image: str):
    imageBytes = base64.b64decode(b64Image)
    image = Image.open(io.BytesIO(imageBytes))

    result = parse(image)
    return {"result": result}
