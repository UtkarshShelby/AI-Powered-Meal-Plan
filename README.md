# Family Meal Planner Frontend

A React Native mobile application for managing family meal plans.

## Features

- User Authentication with OTP-based email verification
- User Profile Management
- Family Member Management
- Calorie Requirement Calculation
- Food Preference and Allergy Management

## Tech Stack

- React Native
- Expo
- TypeScript
- React Navigation
- React Native Paper
- Axios
- AsyncStorage

## Prerequisites

- Node.js (v14 or higher)
- npm or yarn
- Expo CLI
- Android Studio (for Android development)
- Xcode (for iOS development, macOS only)

## Setup Instructions

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the development server:
   ```bash
   npm start
   ```

3. Run on Android:
   ```bash
   npm run android
   ```

4. Run on iOS:
   ```bash
   npm run ios
   ```

## Project Structure

```
src/
  ├── assets/         # Images, fonts, and other static files
  ├── components/     # Reusable UI components
  ├── constants/      # App constants and theme configuration
  ├── hooks/         # Custom React hooks
  ├── navigation/    # Navigation configuration
  ├── screens/       # Screen components
  ├── services/      # API services
  └── utils/         # Utility functions
```

## API Integration

The app communicates with the backend API running at `http://localhost:8080/api`. Make sure the backend server is running before testing the app.

## Development

- Use TypeScript for type safety
- Follow the existing code style and structure
- Write meaningful commit messages
- Test thoroughly before submitting PRs

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request
