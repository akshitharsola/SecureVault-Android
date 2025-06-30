# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

SecureVault is an Android password manager application built with Kotlin and Jetpack Compose. The app provides secure password storage with biometric authentication, encryption, and backup/restore functionality.

## Build Commands

### Building the app
```bash
./gradlew build
```

### Running tests
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Creating APK
```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

### Linting
```bash
./gradlew lint
```

## Architecture

The app follows Clean Architecture principles with these key layers:

### Core Components
- **data/**: Data layer with Room database, entities, and DAOs
- **domain/**: Business logic with use cases
- **ui/**: Presentation layer with Compose screens and ViewModels
- **di/**: Dependency injection using manual DI (AppModule)
- **utils/**: Utility classes for security, backup, biometric, and file operations

### Key Architecture Details

**Database**: Room database with `PasswordEntity` storing encrypted password data
**Security**: 
- Biometric authentication with PIN fallback
- AES encryption for password storage and backups
- Android Keystore integration via `SecurityManager`

**Navigation**: Single-activity architecture using Compose Navigation with these screens:
- MainScreen: Password list with search
- FormScreen: Add/edit passwords
- DetailScreen: View password details
- SettingsScreen: App settings and backup/restore

**State Management**: ViewModels with Repository pattern, using Compose state for UI

### Dependencies Management
- Uses Gradle version catalogs (`gradle/libs.versions.toml`)
- Key dependencies: Compose, Room, Biometric, Security Crypto, Gson
- Kotlin kapt for Room annotation processing

### Security Features
- `BiometricHelper`: Handles biometric authentication setup
- `BackupEncryption`: Encrypts/decrypts backup files
- `SecurityManager`: Manages encryption keys via Android Keystore
- `ClipboardManager`: Secure clipboard operations with auto-clear

## Testing
- Unit tests in `src/test/`
- Instrumented tests in `src/androidTest/`
- Uses JUnit 4 and Espresso for testing