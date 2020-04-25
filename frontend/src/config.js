/* eslint-disable */
let config;

if (process.env.NODE_ENV === "production") {
  config = {
    apiUrl: location.origin,
    imgServiceApiUrl: location.origin
  }
} else {
  config = {
    apiUrl: `http://localhost:8081`,
    imgServiceApiUrl: `http://localhost:8081`
  }
}

export default config