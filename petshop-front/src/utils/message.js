import Vue from "vue";
import NotificationsPlugin from "@/components/NotificationPlugin";
const TYPE = ["", "info", "success", "warning", "danger"];
function showMsg(msg, ...rest) {
    const [type, horizontalAlign, verticalAlign] = rest;
    NotificationsPlugin.$store.notify({
        message: msg,
        icon: "add_alert",
        timeout: 10000000,
        horizontalAlign: horizontalAlign || "center",
        verticalAlign: verticalAlign || "top",
        type: type || "",
    });
}

export function ErrMsg(msg, ...rest) {
    showMsg(msg, "danger", ...rest);
}

export function SucMsg(msg, ...rest) {
    showMsg(msg, "info", ...rest);
}

export function WarMsg(msg, ...rest) {
    showMsg(msg, "warning", ...rest);
}