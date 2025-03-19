<?php
/**
 * Plugin Name: Random Ads Before Post Content
 * Description: Wyświetla losowe ogłoszenie przed treścią posta.
 * Version: 1.1
 * Author: Jakub Borsuk i Aleksander Stepaniuk
 */

if (!defined('ABSPATH')) {
    exit; // Apparently good practice
}

function random_ads_admin_menu() {
    add_options_page('DisplayAds', 'DisplayAds', 'manage_options', 'random_ads', 'random_ads_settings_page');
}
add_action('admin_menu', 'random_ads_admin_menu');

function random_ads_register_settings() {
    register_setting('random_ads_options_group', 'random_ads_list');
}
add_action('admin_init', 'random_ads_register_settings');

// Strona ustawień pluginu
function random_ads_settings_page() {
    ?>
    <div class="wrap">
        <h2>Ustawienia ogłoszeń</h2>
        <form method="post" action="options.php">
            <?php
            settings_fields('random_ads_options_group');
            do_settings_sections('random_ads_options_group');
            ?>
            <textarea name="random_ads_list" rows="10" cols="50" class="large-text"><?php echo esc_textarea(get_option('random_ads_list', '')); ?></textarea>
            <p>Seperate the ads by enter line.</p>
            <?php submit_button(); ?>
        </form>
    </div>
    <?php
}

// Dodanie ogłoszenia do treści posta
function random_ads_before_post($content) {
    if (is_single() && in_the_loop() && is_main_query()) {
	//gets the array of ads
        $ads = explode("\n", get_option('random_ads_list', ''));
        $ad = !empty($ads) ? trim($ads[array_rand($ads)]) : '';
        if (!empty($ad)) {
            $content = '<div class="random-ad">' . $ad . '</div>' . $content;
        }
    }
    return $content;
}
add_filter('the_content', 'random_ads_before_post');

// Dodanie styli do ogłoszenia
function random_ads_styles() {
    echo '<style>.random-ad { background: #f8f8f8; padding: 10px; border: 1px solid #ddd; margin-bottom: 15px; }</style>';
}
add_action('wp_head', 'random_ads_styles');
