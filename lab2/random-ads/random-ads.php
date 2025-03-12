<?php
/**
 * Plugin Name: Random Ads Before Post Content
 * Description: Wyświetla losowe ogłoszenie przed treścią posta.
 * Version: 1.2
 * Author: Jakub Borsuk i Aleksander Stepaniuk
 */

if (!defined('ABSPATH')) {
    exit;
}

// menu w panelu administracyjnym
function random_ads_admin_menu() {
    add_options_page('DisplayAds', 'DisplayAds', 'manage_options', 'random_ads', 'random_ads_settings_page');
}
add_action('admin_menu', 'random_ads_admin_menu');

// rejestracja ustawień jako tako
function random_ads_register_settings() {
    register_setting('random_ads_options_group', 'random_ads_list');
}
add_action('admin_init', 'random_ads_register_settings');

// strona ustawień
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
            <p>Wpisz ogłoszenia w formacie: <code>Treść ogłoszenia | YYYY-MM-DD</code>. Oddziel każde ogłoszenie nową linią.</p>
            <?php submit_button(); ?>
        </form>
    </div>
    <?php
}

// funkcja wybierająca losowe ogłoszenie
function get_random_ad() {
    $ads = explode("\n", get_option('random_ads_list', ''));
    $valid_ads = [];

    foreach ($ads as $ad) {
        $parts = explode('|', trim($ad));
        $ad_text = trim($parts[0]);
        $expiry_date = isset($parts[1]) ? trim($parts[1]) : null;

        if (!$expiry_date || strtotime($expiry_date) >= time()) {
            $valid_ads[] = $ad_text;
        }
    }

    return !empty($valid_ads) ? $valid_ads[array_rand($valid_ads)] : '';
}

// dodanie ogłoszenia do treści posta
function random_ads_before_post($content) {
    if (is_single() && in_the_loop() && is_main_query()) {
        $ad = get_random_ad();
        if (!empty($ad)) {
            $content = '<div class="random-ad">' . $ad . '</div>' . $content;
        }
    }
    return $content;
}
add_filter('the_content', 'random_ads_before_post');

// dodanie shortcode do wyświetlania ogłoszenia
function random_ads_shortcode() {
    return '<div class="random-ad">' . get_random_ad() . '</div>';
}
add_shortcode('random_ad', 'random_ads_shortcode');

// dodanie styli cssdo ogłoszenia
function random_ads_styles() {
    echo '<style>
        .random-ad { 
            background: #f8f8f8; 
            padding: 15px; 
            border: 2px solid #0073aa; 
            border-radius: 5px;
            margin-bottom: 20px; 
            text-align: center;
            font-weight: bold;
        }
    </style>';
}
add_action('wp_head', 'random_ads_styles');

// dynamiczne ładowanie ogłoszeń przez AJAX
function random_ads_ajax_script() {
    wp_enqueue_script('random-ads-ajax', plugin_dir_url(__FILE__) . 'random-ads.js', array('jquery'), null, true);

    wp_localize_script('random-ads-ajax', 'randomAdsAjax', array(
        'ajax_url' => admin_url('admin-ajax.php')
    ));
}
add_action('wp_enqueue_scripts', 'random_ads_ajax_script');

// obsługa requestów AJAX
function random_ads_ajax_handler() {
    echo '<div class="random-ad">' . get_random_ad() . '</div>';
    wp_die();
}
add_action('wp_ajax_get_random_ad', 'random_ads_ajax_handler');
add_action('wp_ajax_nopriv_get_random_ad', 'random_ads_ajax_handler');
