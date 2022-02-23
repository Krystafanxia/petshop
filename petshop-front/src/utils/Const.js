export default {
  STATUS: {
    SUCCESS_CODE: '0000',
    ERR_CODE: '0001'
  },
  // 01 金毛 02 泰迪 03是阿拉斯加
  PET_TYPE: {
    '01': 'Dog',
    '02': 'Cat',
    '03': 'Hamster',
    '04': 'Rabbit'
  },
  // 00 待售 01 已售 02 待收养 03 已收养
  SALE_TYPE: {
    '00': 'For sale',
    '01': 'Sold',
    '02': 'For adoption',
    '03': 'Adopted'
  },
  FILE_TYPE: {
    AVATAR: '99',
    BANNER: '00',
    IMG: '01',
    VIDEO: '02'
  },
  USER_TYPE: {
    ADMIN: '00',
    USER: '01'
  },
  VIDEO_TYPE: {
    mp4: 'video/mp4',
    ogg: 'video/ogg',
    webm: 'video/webm'
  },
  VIDEO_OPTIONS: {
    playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度
    autoplay: false, // 如果为true,浏览器准备好时开始回放。
    muted: false, // 默认情况下将会消除任何音频。
    loop: false, // 是否视频一结束就重新开始。
    preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
    language: 'zh-CN',
    aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
    fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
    // sources: [{
    //   type: "video/mp4", // 类型
    //   src: '' // url地址
    // }],
    poster: '', // 封面地址
    notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
    controlBar: {
      timeDivider: true, // 当前时间和持续时间的分隔符
      durationDisplay: true, // 显示持续时间
      remainingTimeDisplay: false, // 是否显示剩余时间功能
      fullscreenToggle: true // 是否显示全屏按钮
    }
  }
};
export function formatter(from) {
  return key => from[key] || '其他';
}
export function defaultKey(from, index = 0) {
  return Object.keys(from)[index];
}
export function options(from) {
  return Object.keys(from).map(key => ({ key, label: from[key], value: from[key] }));
}
export function getKey(from, value) {
  return from[Object.keys(from).find(key => from[key] === value)];
}
export function getValue(from, key) {
  return from[key];
}
