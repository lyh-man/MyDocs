# MkDocs（Windows 上安装并使用）
## 一、简介
Markdown 项目文档工具。

用于构建静态 HTML 站点，比如：通过 Github 作为博客网站访问。

文档使用 Markdown 格式编写，并使用一个 YMAL 文件作为配置文件。

+ [MkDocs 官网地址](https://www.mkdocs.org)
+ [MkDocs 官方文档](https://www.mkdocs.org/user-guide/configuration/)

## 二、安装
### 1、运行环境
MkDocs 需要安装 Python 环境，MkDocs 支持 Python 2.6, 2.7, 3.3 和 3.4。可通过 pip 安装。

### 2、Windows 上安装 Python3
+ [Python3 官网](https://www.python.org/)
+ [download python-3.11.1 for windows](https://www.python.org/ftp/python/3.11.1/python-3.11.1-amd64.exe)

官网下载，此处以 `Python-3.11.1` 为例。

运行 `python-3.11.1-amd64.exe`，并勾选 `Add Python.exe to Path`，安装 Python3。

+ 查看版本
```text
python --version
pip --version
```

### 3、使用 Python 安装 MkDocs
+ 安装
```text
pip install mkdocs
```

+ 查看是否安装成功
```text
pip list
mkdocs --version
```

### 4、MkDocs 命令说明
+ 创建名为 `dir-name` 的项目
```text
mkdocs new [dir-name]
```

+ 在本地启动项目
```text
mkdocs serve
```

+ 构建文档站点
```text
mkdocs build
```

+ 发布文档到 GitHub 上
```text
mkdocs gh-deploy
```

+ 查看 mkdocs 使用帮助
```text
mkdocs help
```

## 三、本地使用
### 1、创建项目
+ 通过 `mkdocs new` 命令创建名为 `MyDocs` 的项目。
```text
mkdocs new MyDocs
```

### 2、目录结构说明
+ 执行 `tree /f` 命令可以输出目录结构
```text
└─MyDocs
  ├─docs             # 文档目录
  │ └─index.md       # 项目默认主页文件
  └─mkdocs.yml       # 配置文件  
```

### 3、本地启动项目
+ 在 `mkdocs.yml` 同级目录，执行 `mkdocs serve` 命令
```text
cd MyDocs/
mkdocs serve
```

+ 控制台输出如下内容
```text
INFO     -  Building documentation...
INFO     -  Cleaning site directory
INFO     -  Documentation built in 0.17 seconds
INFO     -  [01:37:52] Watching paths for changes: 'docs', 'mkdocs.yml'
INFO     -  [01:37:52] Serving on http://127.0.0.1:8000/
```

+ 浏览器访问 `http://127.0.0.1:8000/`，即可查看文档。

+ 当配置文件、文档目录、主题 发生改变时会自动载入并重新生成文档。

### 4、生成 site（可选操作）
+ 执行 `mkdocs build` 命令，根据 文档 生成 html、css、js 等文件。
```text
mkdocs build --clean
```

## 四、在 GitHub 上启动静态资源
+ [Creating a GitHub Pages site](https://docs.github.com/en/pages/getting-started-with-github-pages/creating-a-github-pages-site)

### 1、创建 GitHub 仓库（此处省略）

### 2、push 代码到 GitHub
+ 配置 remote repository 
```text
git remote add origin https://github.com/lyh-man/MyDocs.git
git remote -v
``` 

+ 提交到 local repository
```text
git status
git add .

git commit -m "build mkdocs"
```

+ push to remote
```text
git push -u origin master
# git push -f --set-upstream origin master
```

### 4、启动静态资源
```text
mkdocs gh-deploy
```

## 五、自定义配置 (mkdocs.yml)
### 1、配置项目信息
+ 属性
```text
site_name    必须，设定项目名
repo_url     可选，若设定为 GitHub 地址，则每个页面将会添加一个链接到你的 GitHub 版本库。
```

+ 举例 
```text
site_name: Mashmallow Generator
repo_url: https://gitee.com/lyh-man/fast-template
```

### 2、配置目录
+ 属性
```text
docs_dir    可选，默认为 docs。即配置文件（mkdocs.yml）所在的目录。
site_dir    可选，默认为 site。即配置文件（mkdocs.yml）所在的目录。
```

### 3、配置目录结构
+ 属性
```text
nav   可选，设定 导航栏。
      该选项为一个列表（name: dir）。name 表示标题名(可选，若不写，则默认为文档名)，dir 表示文件相对于 docs_dir 的路径。
      若需配置多级目录，则使用列表嵌套。
```

+ 举例
```yaml
nav:
  - Introduction: 'index.md'
  - level: 
    - level2: 'index.md'
  - 'about.md'
  - 'Issue Tracker': 'https://example.com/'
```

### 4、配置主题
+ 属性
```text
theme                 可选，设定文档展示的主题。
    name              设定主题名，内置主题有 mkdocs 和 readthedocs。 https://www.mkdocs.org/user-guide/choosing-your-theme/
    locale            设定网站语言。
    custom_dir        设定自定义主题的目录，加载本地系统中的自定义主题的目录。
    static_templates  设定静态页面模板的文件。存放于 custom_dir 指定的目录中。 
```

+ 举例
```yaml
theme:
  name: mkdocs
  locale: en
  custom_dir: my_theme_customizations/
  static_templates:
    - sitemap.html
```

### 5、配置第三方主题（mkdocs-material） 
+ [mkdocs-material](https://github.com/squidfunk/mkdocs-material)

+ 安装 mkdocs-material
```yaml
pip install mkdocs-material
```

+ 修正 mkdocs.yml
```yaml
theme:
  name: material
```