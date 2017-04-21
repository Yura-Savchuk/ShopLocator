## Project corresponds to the next requirements:
  * Splash screen with 3 seconds delay. (Even when user rotate screen it must be shown  3 seconds)
  * Shops screen
    * List of shops
    * Shop contains at least name, coordinates, image (can be random image URI), owner (user)
    * List item must display described properties
    * Toolbar with CRUD actions
  * Shop detail screen (duplicate list item information)
  * Provide phone and tablet (master-detail) modes for shops list and shop detail view
  * Map screen
    * Google map which display shops by coordinates
    * Search bar (filter shops by name)
    * ViewPager with shop item (can looks like shop list item in shops screen) - when user swipe viewpager to concrete shop then map camera move to that coordinates
  * Users screen (list of shops owners) (every owner can have few shops)
  * User detail view (contain user info and list of his shops)
  * Settings screen
    * ability to configure time of splash screen
    * button that send application database by email
  * All data (shops, users) is saved in database
  * Shops and users loaded/synchronized by Firebase RealtimeDatabase
  * Use patterns like MVP
  * Add ability to cache data from server (in case when user cant load data from server when network unavailable it must load them from cache)
  * Provide mechanism for debug build to load data from fake storage. For example when server is unavailable you should configure your build to display fake data
  * Create Unit tests for database CRUD operations
