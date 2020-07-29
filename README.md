# Description
This is an Android client for the Favqs API <https://favqs.com/api>.

# Technical details
- The app follows MVVM+Clean Architecture principles, with separation of concerns between the data and presentation layer.
- The app can show quotes offline. The current session, profile and quotes are saved using Room (ORM over Sqlite) <https://developer.android.com/topic/libraries/architecture/room>)
- Coroutines are used for asynchrony and to prevent blocking the main thread.

# Improvements
- UI/UX can be improved in many ways: loading states, error states, better display of the quotes, etc.
- There aren't any tests at the moment.
