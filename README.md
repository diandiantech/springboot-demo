# 概述
springboot多模块项目基本骨架，主要包含以下模块（类似DDD层次设计）：
1. infra模块：基础实施层，向其他层提供通用的技术能力。基础设施层还能够通过架构框架来支持四个层次间的交互模式。
2. dal模块：数据存储层，用于和数据库交互接口定义与实现。
3. biz模块：业务层，负责核心业务实现。
4. web模块：Rest接口定义。
5. starter模块：启动模块、Docker文件定义。
6. client模块：和外部交互的模型、接口定义。

# 环境依赖
1. maven
2. docker
3. mysql客户端

# 应用启动
1. 执行根目录下脚本：`./cleanRestart.sh`
2. 第一次启动时还需要初始化数据库信息：`./mysqlSetup.sh`

# 示例
1. 添加用户信息：
> http://localhost:8000/user/add?userName=test1&password=test1
```json
{
  "id": 1,
  "gmtCreate": "2020-07-13T13:27:20.000+0000",
  "gmtModified": "2020-07-13T13:27:20.000+0000",
  "name": "test1",
  "password": "test1"
}
```
2. 根据用户名查询用户信息：
> http://localhost:8000/user/findByName?userName=test1
```json
{
  "id": 1,
  "gmtCreate": "2020-07-13T13:27:20.000+0000",
  "gmtModified": "2020-07-13T13:27:20.000+0000",
  "name": "test1",
  "password": "test1"
}
```
3. 查询所有用户信息：
> http://localhost:8000/user/findAll
```json
[
  {
    "id": 1,
    "gmtCreate": "2020-07-13T13:27:20.000+0000",
    "gmtModified": "2020-07-13T13:27:20.000+0000",
    "name": "test1",
    "password": "test1"
  }
]
```
