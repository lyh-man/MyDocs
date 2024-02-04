# MyDocs
## 说明
### 简介
使用 MkDocs 并借助 GitHub 搭建个人博客。
+ [mkdocs 使用](docs/common/tool/mkdocs/mk-docs.md)

### windows 本地启动服务
+ 安装 python-3.11.1 for windows
  + [download python-3.11.1 for windows](https://www.python.org/ftp/python/3.11.1/python-3.11.1-amd64.exe)
  + 运行 `python-3.11.1-amd64.exe`，并勾选 `Add Python.exe to Path`，安装 Python3。
```text
// 查看 python 版本，确认是否安装成功
python --version
pip3 --version
```

+ 使用 Python 安装 MkDocs
```text
pip3 install mkdocs

// 查看是否安装成功
pip3 list
mkdocs --version
```

+ 安装 mkdocs-material 主题
```yaml
pip3 install mkdocs-material
```

+ 在 `mkdocs.yml` 同级目录，执行 `mkdocs serve` 命令。启动本地服务
```text
mkdocs serve
```

### 工程目录
```text
├─code-in-docs         // 博客文档中的案例代码
├─common-tool          // Java 常用代码
└─docs                 // 博客文档
```
