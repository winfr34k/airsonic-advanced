<!--
# CHANGELOG.md
# kagemomiji/airsonic-advanced
# -->

## v11.1.2 - 29 Apr 2023

Fixes:
- Fixed invalid `Shoutcast` response which caused bad behaviors on external players
- Fixed mejs-controls.svg not found error when custom context path is set
- Fixed an issue of overflowed library duration on left side bar when the library contains invalid cue sheets.
- Fixed an issue about `Recently Played` showing duplicated items when using external players.

Changes:
- Replace upload library from `commons-fileupload` to `spring-boot-starter-web`
- The following properties are loaded by ConfigurationProperties instead of SettingsService.
  - airsonic.home
  - airsonic.defaultMusicFolder
  - airsonic.defaultPodcastFolder
  - airsonic.defaultPlaylistFolder

Security:
- Bump up spring-boot-starter-parent to 2.7.11 for CVEs
- Fix some vulnerabilities from Java codes detected by CodeQL

Chores:
- Add troubleshooting document. See [here](docs/troubleshooting.md)
- Treat CodeQL action warning

## v11.1.1 - 5 Apr 2023

> **Note**  
> Clear browser cache to replace old javascript

Fixes:
- Fixed an issue where shuffle play on the More page failed with SQL error
- Fixed an issue where bookmark would not be removed with duplicate key error
- Fixed issue of `/search2` with NullPointerException problem 
- Fixed video player icon issue
- Fixed an issue where buttons on the Bookmark page would not respond when clicked.

Changes:
- Bump up mediaelement-and-player.js to 6.0.2
- Bump up hls.js to 1.3.4
- Bump up dash.js to 4.6.0

Security:
- Fix vulnerabilities from javascript codes detected by CodeQL

Chores:
- Docker build action supports dependabot PR

## v11.1.0 - 17 Feb 2023

Changes:
- :bulb: Support CUE-indexed tracks
- Bump up spring boot version to v2.7.x
- Fix problem IndexWriter at search library
- Fix problem search gives Whitelabel Error Page
- Replace MySQL connector to `mysql-connector-j`

Chrores:
- Treat deprecated WebSecurityConfigurerAdapter
- Modify issue templates

## v11.0.2 - 16 Jan 2023

** Treat code warning and deprecated functions**

Chores:
- Treat deprecated jdbcTemplate methods
- Treat deprecated Assertion methods
- Treat deprecated ObjectMapper methods
- Remove deprecated LastModified annotation
- Change GUI link to this repository
- And some code warning fixed


## v11.0.1 - 30 Dec 2022

**Security update and modify CI for this repository**

Changes:
- Docker base image to eclipse-temurin:17.0.5
- Change base Java version to 17
- Change support DB version
  - HSQLDB: 2.7.1
  - PostgreSQL: 13.3, 14.1
  - MySQL: 8.0
  - MariaDB: 10.5

Security:
- Upgrade springboot to 2.6.14 for CVE
- Upgrade hsqldb to 2.7.1 for CVE
- Replace jdom to jdom2 for CVE
- Upgrade snakeyml to 1.33 for CVE
- Upgrade liquibase-core to 4.18.0 for CVE
- Upgrade apache-commons-text to 1.10.0 for CVE
- Upgrade org.apache.cxf:cxf-core to 3.5.5 for CVE

Chores:
- Modify CI for this repository
- Add dependabot for github-actions auto update
- Add Trivy scan to CI
- Update gpg key
- Modify pom.xml for this repository
- Replace badges on README.md 

## v10.5.0 - 7 Nov 2019

**New index version. Scan will be triggered on startup**

Changes:
- Precompile jsp
- Add option to disable timestamps during scan
- Making textareas vertical-align middle allows them to align with adjacent imgs.
- Improved handling of missing/failed transcoder
- Removed inability to change roles for admin user
- Display file id and path in debug scanning logs
- Update dark icons for the 'groove' theme
- added discogs search link to main album page
- Use titles in some \<img\> tags
- Make the systemd unit compatible with tomcat9
- Make the logout icon/image cliquable
- Bump Spring version
- Improve Windows system detection
- Update Lucene from 3.0.3 to the current version 8.2.0
- Add expunge to IndexManager

Fixes:
- Fix podcast download issues
- Fix null exception when creating a new podcast channel
- Handle Lyrics fetch HttpResponseException
- Added handling for LyricsService ConnectTimeoutException
- Improve error handling for jaudiotaggerParser artwork fetch
- Improve transcoder info text and formatting
- Handle nulls when processing cover art better
- Media file scan will now heed configured music/video file types
- Fix incorrect absolute paths in css
- Fixed broken brace and quote splitting in help text
- Fixed inconsistencies in SVG icon colors
- Remove white underline from player controls
- fixed 'Settings Saved' notification when saving transcoding settings #1114 (#1269)
- Fix broken update link
- normalize license expire date to prevent outstanding value on Java 12
- escaped artist and album values in links; fixed allmusic link
- Fix a NULL-deref in CoverArtController.java
- Gracefully handle failed version check attempts
- Fix the CSS for groove simple
- Path to search in a specific case is incorrect #1139 

Security:
- upgrade jackson-databind and commons-beanutils for CVEs

And lots of code cleanup/improvements!

## v10.4.2 - 22 Oct 2019

### Fixes

- Fix a migration issue on MariaDB (#1333)

## v10.4.1 - 14 Oct 2019

### Fixes

- Last song in a play queue no longer repeats (#1254)
- Add database support for MariaDB (#1188)

### Developer

- Replace dead repository (teleal.org) with a new one (#1277)
- Some dependencies updated to new minor versions in order to fix CVEs
- Some backported changes to make tests pass more reliably

## v10.4.0 - 13 Jul 2019

Fixes:
- /stream When transcoding, always use chunked transfers and report that ranges
  are not supported. When not transcoding, support returning ranges but only if
requested.
- /stream Remove the option to never set Content-Length. This workaround is
  obsolete with correct handling of ranges when requested.
- Use spaces instead of coma in systemd lists
- Don't use ProtectHome by default in the systemd unit
- Show cover art not displaying on chromecast
- Fix downloads with embedded covers
- Fix a crash when transcoding a track without artist/album
- Fix the video player that was broken in cf1f86f
- Fix 3 minor errors in jsp files
    - Add a missing `$` in dlnaSettings
    - Add a missing taglib import in homePage.jsp
    - Add a missing `=` in playQueue.jsp's css
- Fix layout and alignment issues in current master branch
- Work around play queue not going to the next song automatically

Changes:
- Add m4b detection
- Cleanup unused libraries
- Better handling of ID3v2.4
- /stream Refactor
- /stream Dont use RanegOutputStream when entire range is requested
- /stream Omit unnecessary null check on ranges
- Declare the usage of HTML5 in the doctype
- Remove the embedded copy of jaxrpc
- Fix a possible stacktrace on RandomPlayQueue
- Add contrib/library\_autoupdater.sh (#1096)
- Remove the deprecated getAlphabetialAlbums method
- Remove the ghetto REST flash-based video player
- Add an `alt` attribute to images, to increase accessibility
- Use SVG for the icons for the default\_light theme
- Disallow deleting your own user or removing admin role
- SearchService refactoring
- Play internet radios in MediaElement (fix #408)
- Add internet radio source cache
- Hide play queue actions while playing internet radios
- Limit the amount of data we parse from remote internet radio playlists
- Show an error is an internet radio has no sources to load
- Do not try to load radio playlists in the browser when clicking on a radio
- Follow HTTP redirects when loading internet radio playlists

Translation Updates:
- Character encoding changes
- Strip the \<p>…\</p> markup in translation
- Add Weblate config

Security:
- Cookie is now marked httpOnly
- Jackson updates


## v10.3.1 - 21 May 2019

Fixes:
  * Fix utils.js naming issue

Security:
  * CVE-2019-12086 - bump jackson version

## v10.3.0 - 20 May 2019

Fixes:
  * Fix a javascript null-deref occuring when localstorage isn't available/supported by the browser
  * Fix StringIndexOutOfBounds errors thrown by DWR/YUI
  * Fix a small resource leak
  * Fix #611 Add support for Java 9 and greater
  * Fix typo in anonymous user name (#663)


Changes:
  * Maven Dependency Updates
  * HSQL: Checkpoint/defrag/shutdown optimizations
  * HSQL: Log tweaks
  * Remove momentjs, pngfix
  * Codebase modernization
  * Systemd unit hardening
  * Remove the external fonts usage
  * Update mediaelement from 4.1.1 to 4.2.9
  * Remove script.aculo.us dependency
  * Add description and viewport meta tags
  * Javascript cleanup/optimizations
  * Remove Google+ relics
  * Remove jquery-contextmenu since it's not used anywhere
  * Remove webfx stuff, since they're apparently not used
  * Remove WAP-related stuff
  * Do not show stack trace for client-side connection errors
  * Show more informative messages while streaming
  * Replace latin encoding with utf-8
  * Don't autocomplete the password field
  * Clicking on the logo now redirects to home instead of about
  * Remove mentions of subsonic premium
  * Add a permission check for the podcast folder
  * Replace the double-mustache anti-pattern
  * Remove the /db page
  * Bump jQuery/jquery-ui to the latest versions
  * Replace the flash player with medialement.js for the shared media
  * Add system properties for persisting the 'remember me' key
  * Replace the usage of prototypejs with jquery in changeCoverArt.jsp
  * Add the required keyword to some forms
  * Update docker base image to alpine 3.9


Security:
  * CVE-2019-10908 Generate new passwords in a secure way
  * Fix a xss and clean up some js
  * CVE-2019-10907 Use a random key to "encrypt" the remember-me cookie's value
  * Use https for external links
  * Fix a bunch of dom-based xss
  * Add a noopener and noreferrer to external urls
  * Avoid logging sensitive URL parameters in the Subsonic API
  * Fix various minor issues found by LGTM

## v10.2.1 - 18 Feb 2019

Security:
  * CVE-2018-20222 Prevent xxe during parse

## v10.2.0 - 16 Feb 2019

Fixes:
  * Fix #658 again: content type for unscaled images set based on jaudiotagger output
  * Reverted a93a18a and properly re-encoded with 'native2ascii -encoding UTF-8'
  * fix issues #638 and #574
  * Fix #596 SubStandard theme includes old Subsonic Logo
  * Fix themes using dark background with me\_js
  * Remove potential cast exception
  * Fixed github link opening in frame and not loading
  * Correct corrupted downloaded zip
  * Fix #778: Defragment embedded HSQLDB database more frequently
  * PlayQueue: Fix broken keyboard shortcuts
  * Fix #860 (external database performance) by using connection pooling … (#864)


Changes:
  * Made it easier to see current playing song for dark themes
  * Spring Boot 1.5.18
  * New add\_album to play queue
  * Remove margin of media\_control bar
  * Update to 3.3.0 java-jwt
  * catch exceptions ClientAbortException display a short message and return, to avoid the massive useless traceback in log
  * Update cxf to 3.1.15
  * Issue #164: Show link to MusicBrainz release on album pages
  * Handle player id as an Integer instead of Strin
  * Add Docker health check
  * Use dark media player theme on groove theme (#777)
  * Change to optional reCAPTCHA v2
  * Optionally parse podcast episode duration in seconds to [hh:]mm:ss
  * Add option to disable seeking on transcodes. (Mitigates #548 & #723)
  * White list jars that are scanned for tlds to prevent spurious logs
  * Tweaked logging around servlet container and added warning about jetty
  * Add extended favicons
  * Display folders as a list in Settings-\>Users and include the path.
  * Add 32x32 pixeled favicon / updated favicons in webapp
  * Updated internal maven plugins


Translation Updates:
  * Fixed elipse in english translation

Security:
  * Fix #749 Ensure transcode settings are protected
  * Bump version of guava to deal with CVE-2018-10237
  * Update jackson version
  * Fix #764 LDAP Login allows unauthenticated access

Not Fixed:
  * #685 - transcoding length issue

## v10.1.2 - 28 Aug 2018

Fixes:
  * Fix LDAP authentication bypass

## v10.1.1 - 16 Dec 2017

Changes:
  * Add show-all button on artist landing page
  * Upgrade jaudiotagger to 2.2.5 supporting Java 9

Fixes:
  * DLNA Recent Albums is just listing albums
  * NPE in docker container
  * Substandard theme css
  * Build error causing Jetty to be default container (should be Tomcat)

Translation Updates:
  * English language cleanup

## v10.1.0 - 04 Nov 2017

Changes:
  * New Jukebox player using javasound api
  * Localize artist bios from last.fm
  * Use `ffprobe` and not `ffmpeg` to scrape metadata
  * Added options for excluding files during scan (symlinks and regex)
  * Add "opus" and "mka" extension to default extension list

Fixes:
  * Error message readability
  * Adding album comment
  * Subsonic API wrong error behavior for getAlbumList
  * Stop airsonic from creating double slashes in urls.
  * Search csrf timeout/expiration
  
Security:
  * CVE-2014-3004 - XML playlist parsing

Translation Updates:
  * English

## v10.0.1 - 23 Aug 2017

Note that with this release, the jdbc-extra flavored war is now the default and only war.

  * Translation updates for French, Bulgarian, German, Italian, Spanish,
  * Docker image tweaks
  * Some light cleanup/refactorings
  * Fixed password reset
  * Fixed broken liquibase when airsonic.defaultMusicFolder is modified

## v10.0.0 - 06 Aug 2017

  * Rebranded to Airsonic
  * Replaced JWplayer with MediaElement.js (HTML5 player)
  * Upgraded to Subsonic API version 1.15
  * Added official Docker image
  * Added Airsonic to a Translation service (Weblate)
  * Some translations updates (English, French, German, and Russian)
  * New login page
  * Added additional war with builtin support for external databases
  * Improved playlist handling
  * DLNA browsing improvements
  * Small fixes and improvements

## v6.2 - 02 May 2017

  * Small fixes
  * Release only a month behind schedule! We're improving!

## v6.2.beta4 - 25 Apr 2017

  * Final fixes in Beta! Release soon

## v6.2.beta3 - 08 Apr 2017

  * API endpoint security tightening
  * More documentation
  * Less licensing code
  * More cowbell

## v6.2.beta2 - 24 Mar 2017

  * Add database settings UI
  * Documentation improvements
  * Lots of spit and polish

## v6.2.beta1 - 05 Mar 2017

  * Add external database support
  * Upgrade to new version of Spring
  * Replace subsonic-booter with Spring Boot
  * Remove remote-access service and port-forwarding
  * Remove vestigial Subsonic licensing calls
  * Add a demo site
  * Tests and bugfixes and documentation, oh my!

## v6.1 - 27 Nov 2016

  * First real stable release!

## v6.1.beta2 - 19 Nov 2016

  * Metaproject: Jenkins builds!
  * More documentation
  * Translation updates
  * Improve shuffling behaviour
  * Lots of small fixes, many more to come

## v6.1.beta1 - 15 May 2016

  * Meant as a release candidate; failed to make it past the Primary election.

## v6.1-alpha1 - 14 May 2016

  * Search+replace subsonic-->libresonic
  * Move out of org.sourceforge.subsonic namespace
  * Develop becomes horribly unstable, you shouldn't be using this.

## v6.0.1 - 14 May 2016

  * First recommended release
  * Updated Help/About page text

## v6.0 - 1 May 2016

  * First release as Libresonic
  * Based upon Subsonic 5.3(stable)
