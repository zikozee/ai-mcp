# AI ASSISTANT MCP
- LLM takes care of the orchestration of tool calls flow by checking the available tools

- Tools are best for actions and dynamic queries
- A prompt can guide the model to use tools in a particular way, but it doesn’t replace the tools themselves.
- Resources are best used for retrieving stable artifacts such as documents, configuration data, or knowledge assets
- Completions provide suggestions and autocomplete
  - client can call completions/list to see available list 
  - client can also provide partial values  to completions/complete and the server returns suggested completions
  - This ultimately guide clients to construct valid commands or tools parameters
  - best used in Coding assistants, IDEs, CLI tools, Developer platforms