# DeepSeek-ollama-mingxin
## 作者
depeng wang
## 联系我
wangdepeng97@163.com
## 项目简介
本次项目采用SpringBoot3+ollama+SpringAI+SSE+modelFile+mysql+mybatis+maven
### Project Thinking
SpringAI的实现效果不及Python那么好对于大模型来说，但是可以做应用层面，没有FunctionCalling
听说spring-ai-alibaba改了很多东西后期可以试一试

## 项目特点
- **Java实现DeepSeek的Ollama的提示词**：总结ollama的开放API非常的拉跨，没有rag记忆功能(有大佬的话可以给我指正)
- **SSE**：SSEServer流式输出效果非常好
- **Prompt**：modelFile的提示词还是不错的，采用CO-START的提示词，效果不错