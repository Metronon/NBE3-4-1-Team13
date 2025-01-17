import createClient

const client = createClient<paths>({
  baseUrl: "http://localhost:8080",
  credentials: "include",
});

export default client;