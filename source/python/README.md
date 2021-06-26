# Environment setup

0. Install python version 3.8.5 

1. Create a python virtual enviornment 

```shell
# python -m venv /path/to/new/virtual/environment
python -m venv ~/venvs/open_data
```

Seealso: 
- https://docs.python.org/3/library/venv.html
- https://code.visualstudio.com/docs/python/environments

2. Activate de virtual enviornment

```shell
source ~/venvs/open_data/bin/activate
```

3. Install pyspark

See packages installed:

```shell
pip list
```
Install the package:

```shell
pip install pyspark==3.1.1
```

Check installation:

```shell
pip list

# Package       Version
# ------------- -------
# pip           20.0.2 
# pkg-resources 0.0.0  
# py4j          0.10.9 
# pyspark       3.1.1  
# setuptools    44.0.0 
```

4. On Vscode, create one workspace on the root folder with at least:

```json
{
	"folders": [
		{
			"path": "."
		}
	],
	"settings": {
		"python.pythonPath": "~/venvs/open_data"
	}
}
```