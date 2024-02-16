# 定制化代码生成项目代码示例

> 存放项目中所用到的基础模版代码，用于制作代码生成器


# 笔记

>   Path destPath = dest.toPath().resolve(src.getName());
    Files.copy(src.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
# 如果没有将src的文件名传入，则无法正确生成文件
# 仅用dest.toPath()的路径是我们遍历到的文件的父节点，则我们在目标路径也只会生成以父节点为文件名字的文件，无法生成文件夹
# D:\Code-generrator_total\generator\acm-template