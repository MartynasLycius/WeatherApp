import { PolymerElement } from '@polymer/polymer/polymer-element.js';

class VaadinGeoLocation extends PolymerElement {
    static get is() {
        return "vaadin-geo-location";
    }

    // noinspection JSUnusedGlobalSymbols
    static get properties() {
        return {
            highAccuracy: Boolean,
            watch: Boolean,
            timeout: Number,
            maxAge: Number
        };
    }
    static get observers() {
        return [
            '_start(highAccuracy, watch, timeout, maxAge)'
        ]
    }

    // noinspection JSUnusedGlobalSymbols

    constructor() {
        super();
    }

    // noinspection JSUnusedGlobalSymbols
    request() {
        this._start()
    }

    _start(highAccuracy, watch, timeout, maxAge) {
        if(this._id) {
            navigator.geolocation.clearWatch(this._id);
            this._id = null
        }
        if(watch) {
            let me = this;
            let pos = function (p) {
                me.location = p;
                me.error = null;
                me.dispatchEvent(new CustomEvent('location', {detail: p}));
            };
            let err = function (e) {
                me.location = null;
                me.error = e;
                me.dispatchEvent(new CustomEvent('error', {detail: e}));
            };
            let options = {
                enableHighAccuracy: highAccuracy,
                timeout: timeout,
                maximumAge: maxAge
            };
            this._id = navigator.geolocation.watchPosition(pos, err, options);
        }
    }

}

customElements.define(VaadinGeoLocation.is, VaadinGeoLocation);